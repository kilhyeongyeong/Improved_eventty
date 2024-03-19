package com.eventty.authservice.applicaiton.service.subservices.factory;

import com.eventty.authservice.api.dto.response.GoogleUserInfoResponseDTO;
import com.eventty.authservice.api.dto.response.NaverResponseDTO;
import com.eventty.authservice.api.dto.response.NaverTokenResponseDTO;
import com.eventty.authservice.applicaiton.dto.OAuthAccessTokenDTO;
import com.eventty.authservice.applicaiton.dto.OAuthUserInfoDTO;
import com.eventty.authservice.applicaiton.service.subservices.factory.config.NaverProperties;
import com.eventty.authservice.domain.Enum.OAuth;
import com.eventty.authservice.domain.entity.OAuthUserEntity;
import com.eventty.authservice.domain.exception.OAuthFailGetAccessTokenException;
import com.eventty.authservice.domain.exception.OAuthNotFoundUserInfoException;
import com.eventty.authservice.domain.exception.OAuthNotFoundVerifiedEmailException;
import com.eventty.authservice.domain.repository.OAuthUserRepository;
import com.eventty.authservice.presentation.dto.request.OAuthLoginRequestDTO;
import com.eventty.authservice.api.dto.response.NaverUserInfoResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service("naverOAuthService")
public class NaverOAuthService implements OAuthService {

    private final RestTemplate restTemplate;
    private final OAuthUserRepository oAuthUserRepository;
    private final EntityManager em;
    private final NaverProperties properties;
    private final ObjectMapper objectMapper;

    @Autowired
    public NaverOAuthService(RestTemplate restTemplate, OAuthUserRepository oAuthUserRepository,
                             EntityManager em, NaverProperties naverProperties, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.oAuthUserRepository = oAuthUserRepository;
        this.em = em;
        this.properties = naverProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuthAccessTokenDTO getToken(OAuthLoginRequestDTO oAuthLoginRequestDTO){

        HttpEntity<MultiValueMap<String, String>> entity = createTokenHttpEntity(oAuthLoginRequestDTO.getCode());

        log.info("API 호출 From: {} To: {} Purpose: {}",
                "Auth Server", "Naver Auth Server", "Request naver access token");

        ResponseEntity<NaverTokenResponseDTO> response = restTemplate.exchange(
                OAuth.NAVER.getTokenUri(), OAuth.NAVER.getTokenMethod(), entity, NaverTokenResponseDTO.class
        );

        if (response.getBody() == null)
            throw new OAuthFailGetAccessTokenException(OAuth.NAVER.getSocialName());



        return new OAuthAccessTokenDTO(response.getBody().getAccessToken(), response.getBody().getTokenType());
    }

    @Override
    public OAuthUserInfoDTO getUserInfo(OAuthAccessTokenDTO oAuthAccessTokenDTO) {

        HttpEntity<Void> entity = createUserInfoHttpEntity(oAuthAccessTokenDTO);

        log.info("API 호출 From: {} To: {} Purpose: {}",
                "Auth Server", "Naver Resource Server", "Request Naver user info");

        ResponseEntity<NaverResponseDTO> response = restTemplate.exchange(
                OAuth.NAVER.getUserInfoUri(), OAuth.NAVER.getUserInfoMethod(), entity, NaverResponseDTO.class
        );

        log.debug("Naver Response Body Json: {}", response.getBody());

        if (response.getBody() == null)
            throw new OAuthNotFoundUserInfoException(OAuth.NAVER.getSocialName());
        if (response.getBody() == null)
            throw new OAuthNotFoundVerifiedEmailException(OAuth.NAVER.getSocialName());

        // 네이버는 response로 한번 더 감싸져 있어서 parsing 해주기
        NaverUserInfoResponseDTO body = response.getBody().getResponse();

        log.debug("Api Call 성공:: Client ID: {}", body.getId());

        return new OAuthUserInfoDTO(
                body.getId(),
                body.getEmail(),
                body.getName(),
                convertLocaleDate(body.getBirthyear(), body.getBirthday()),
                body.getMobile(),
                body.getProfileImage()
        );
    }

    @Override
    public Optional<OAuthUserEntity> findOAuthUserEntity(String clientId) {
        // 예외 발생 X
        return oAuthUserRepository.findOAuthUserEntityBySocialNameAndClientId(OAuth.NAVER.getSocialName(), clientId);
    }

    @Override
    public Long create(OAuthUserEntity oAuthUserEntity) {

        em.persist(oAuthUserEntity);

        return oAuthUserEntity.getId();
    }

    private NaverTokenResponseDTO parsingToken(ResponseEntity<String> response) {
        NaverTokenResponseDTO result = null;
        try {
            return objectMapper.readValue(response.getBody(), NaverTokenResponseDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Naver Token parsing Error occurred: {}", e.getMessage());
            throw new OAuthFailGetAccessTokenException(OAuth.NAVER.getSocialName());
        }
    }

    private HttpEntity<MultiValueMap<String, String>> createTokenHttpEntity(String authorizationCode) {

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        body.add("client_id", properties.getClient_id());
        body.add("client_secret", properties.getClient_secret());
        body.add("redirect_uri", properties.getRedirect_url());
        body.add("grant_type", "authorization_code");

        return new HttpEntity<>(body, headers);
    }
    private HttpEntity<Void> createUserInfoHttpEntity(OAuthAccessTokenDTO oAuthAccessTokenDTO) {
        HttpHeaders headers = new HttpHeaders();

        log.debug("Access Token: {}", oAuthAccessTokenDTO.accessToken());
        log.debug("Access Token Type: {}", oAuthAccessTokenDTO.tokenType());
        headers.add("Authorization", oAuthAccessTokenDTO.tokenType() + " " + oAuthAccessTokenDTO.accessToken());

        return new HttpEntity<>(headers);
    }

    private LocalDate convertLocaleDate(String birthyear, String birthday) {

        if (birthyear == null || birthday == null)
            return null;

        // 문자열 합치기
        String fullBirthDate = birthyear + "-" + birthday;

        // 문자열을 LocalDate로 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fullBirthDate, formatter);
    }
}
