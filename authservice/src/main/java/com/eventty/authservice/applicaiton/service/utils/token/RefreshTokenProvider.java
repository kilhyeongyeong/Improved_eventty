package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.applicaiton.dto.ValidateRefreshTokenDTO;
import com.eventty.authservice.domain.entity.AuthUserEntity;
import com.eventty.authservice.domain.entity.RefreshTokenEntity;
import com.eventty.authservice.domain.exception.InValidRefreshTokenException;
import com.eventty.authservice.domain.exception.RefreshTokenNotFoundException;
import com.eventty.authservice.domain.repository.RefreshTokenRepository;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RefreshTokenProvider {

    private final TokenProperties tokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generate(AuthUserEntity AuthUserEntity, Date now, Date expiry) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(AuthUserEntity.getEmail())
                .claim("userId", AuthUserEntity.getId())
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecretKey())
                .compact();
    }

    public RefreshTokenEntity saveOrUpdate(String name, Long userId) {
        // 기존에 저장되어 있는 RefreshToken이 있는지 확인
        Optional<RefreshTokenEntity> existingTokenOpt = refreshTokenRepository.findByUserId(userId);

        if (existingTokenOpt.isPresent()) {
            // 이미 존재하는 경우, token을 업데이트하고 저장
            RefreshTokenEntity existingToken = existingTokenOpt.get();
            existingToken.setName(name);
            return refreshTokenRepository.save(existingToken);
        } else {
            // 존재하지 않는 경우, 새로운 엔터티를 생성하고 저장
            RefreshTokenEntity newRefreshToken = RefreshTokenEntity.builder()
                    .userId(userId)
                    .name(name)
                    .build();

            return refreshTokenRepository.save(newRefreshToken);
        }
    }

    public void validationCheck(ValidateRefreshTokenDTO validateRefreshTokenDTO) {

        // userId 이용해서 저장되어 있는 Enitty 가져오기
        Optional<RefreshTokenEntity> existedRefreshToken = refreshTokenRepository.findByUserId(validateRefreshTokenDTO.userId());

        // userId로 저장되어 있는 refreshToken이 없던가, Matching에 실패한 경우 예외 발생
        if (existedRefreshToken.isEmpty())
            throw new RefreshTokenNotFoundException(validateRefreshTokenDTO.userId());
        if (!existedRefreshToken.get().getName().equals(validateRefreshTokenDTO.refreshToken()))
            throw new InValidRefreshTokenException(validateRefreshTokenDTO);

    }

    public String delete(Long userId) {
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findByUserId(userId);

        if (refreshTokenEntity.isEmpty())
            return "";

        String deletedRefreshToken = refreshTokenEntity.get().getName();
        refreshTokenRepository.delete(refreshTokenEntity.get());
        return deletedRefreshToken;

    }
}
