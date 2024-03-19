package com.eventty.authservice.applicaiton.service.utils.token;

import com.eventty.authservice.domain.entity.AuthUserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class AccessTokenProvider {

    private final TokenProperties tokenProperties;

    public String generateToken(AuthUserEntity AuthUserEntity, Date now, Date expiry) {

        Claims claims = Jwts.claims().setSubject(AuthUserEntity.getEmail());

        claims.put(TokenEnum.USERID.getName(), AuthUserEntity.getId());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setHeaderParam("alg", "HS256")
                .addClaims(claims)
                .setIssuer(tokenProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(AuthUserEntity.getEmail())
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getSecretKey())
                .compact();
    }
}
