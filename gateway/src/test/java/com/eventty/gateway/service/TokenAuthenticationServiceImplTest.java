//package com.eventty.gateway.service;
//
//import com.eventty.gateway.api.ApiClient;
//import com.eventty.gateway.dto.TokenDetails;
//import com.eventty.gateway.global.dto.ResponseDTO;
//import com.eventty.gateway.global.dto.SuccessResponseDTO;
//import com.eventty.gateway.utils.CustomMappper;
//import com.eventty.gateway.utils.jwt.JwtUtils;
//import com.eventty.gateway.utils.TokenEnum;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class TokenAuthenticationServiceImplTest {
//
//    @InjectMocks
//    private TokenAuthenticationServiceImpl tokenAuthenticationService;
//
//    @Mock
//    private ApiClient apiClient;
//    @Mock
//    private JwtUtils jwtUtils;
//    @Mock
//    private CustomMappper customMappper;
//
//
//    @Test
//    @DisplayName("만료기간이 지나지 않은 Access Token을 이용해서 TokenDetails 받아오기")
//    public void getTokenDetails_NOT_EXPIRED_SUCCESS() {
//        // Given
//        String userId = "1";
//        String userRole = "USER";
//        MultiValueMap<String, HttpCookie> cookies = createCookies();
//        TokenDetails tokenDetails = initTokenDetails();
//        Claims claims = createValidatedClaims();
//
//        when(jwtUtils.initTokenDetails(cookies)).thenReturn(tokenDetails);
//        when(jwtUtils.getClaimsOrNullOnExpiration(tokenDetails.getAccessToken())).thenReturn(claims);
//        when(jwtUtils.getUserId(tokenDetails.getAccessToken())).thenReturn(userId);
//        when(jwtUtils.getAuthoritiesToJson(any(Claims.class))).thenReturn(userRole);
//
//        // When
//        TokenDetails result = tokenAuthenticationService.getTokenDetails(cookies);
//
//        // Then
//        assertEquals(tokenDetails.getUserId(), result.getUserId());
//        assertEquals(tokenDetails.getAccessToken(), result.getAccessToken());
//        assertEquals(tokenDetails.getAuthoritiesJson(), result.getAuthoritiesJson());
//    }
//
//    @Test
//    @DisplayName("Access Token이 만료 기간이 지나서 원격 API 호출로 새로 받아온 토큰일 이용해서 Token Details 받아오기")
//    public void getTokenDetails_EXPIRED_SUCCESS() {
//        // Given
//        String userId = "1";
//        String userRole = "USER";
//
//        MultiValueMap<String, HttpCookie> cookies = createCookies();
//        TokenDetails tokenDetails = initTokenDetails();
//        Claims claims = createValidatedClaims();
//
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO(tokenDetails, userId);
//
//        ResponseEntity<ResponseDTO<NewTokensResponseDTO>> response = createSuccessResponse();
//
//        when(jwtUtils.initTokenDetails(cookies)).thenReturn(tokenDetails);
//        when(jwtUtils.getClaimsOrNullOnExpiration(tokenDetails.getAccessToken())).thenReturn(null);
//        when(jwtUtils.getUserId(tokenDetails.getRefreshToken())).thenReturn(userId);
//        when(customMappper.createGetNewTokensRequestDTO(userId, tokenDetails.getRefreshToken())).thenReturn(getNewTokensRequestDTO);
//        when(apiClient.getNewTokens(getNewTokensRequestDTO)).thenReturn(response);
//
//        when(jwtUtils.createNewTokenDetails(response)).thenReturn(TokenDetails.builder()
//                .accessToken(response.getBody().getSuccessResponseDTO().getData().getAccessToken())
//                .refreshToken(response.getBody().getSuccessResponseDTO().getData().getRefreshToken())
//                .claims(claims)
//                .build());
//
//        when(jwtUtils.getAuthoritiesToJson(any(Claims.class))).thenReturn(userRole);
//
//        // When
//        TokenDetails result = tokenAuthenticationService.getTokenDetails(cookies);
//
//        // Then
//        assertNotEquals("Access Toeken", result.getAccessToken());
//        assertEquals("New_Access_Token", result.getAccessToken());
//
//        assertNotEquals("Refresh Token", result.getRefreshToken());
//        assertEquals("New_Refresh_Token", result.getRefreshToken());
//
//        assertEquals("1", result.getUserId());
//    }
//
//    private ResponseEntity<ResponseDTO<NewTokensResponseDTO>> createSuccessResponse() {
//        NewTokensResponseDTO newTokensResponseDTO = NewTokensResponseDTO.builder()
//                .refreshToken("New_Refresh_Token")
//                .accessToken("New_Access_Token")
//                .build();
//        ResponseDTO<NewTokensResponseDTO> responseDTO = ResponseDTO.of(SuccessResponseDTO.of(newTokensResponseDTO));
//
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    private GetNewTokensRequestDTO createGetNewTokensRequestDTO(TokenDetails tokenDetails, String userId) {
//        return GetNewTokensRequestDTO.builder()
//                .refreshToken(tokenDetails.getRefreshToken())
//                .userId(Long.parseLong(userId))
//                .build();
//    }
//    private Claims createValidatedClaims() {
//        String email = "example1@mm.mm";
//        String userId = "1";
//        String authorities = "USER";
//        Claims claims = Jwts.claims().setSubject(email);
//        claims.put(TokenEnum.USERID.getName(), userId);
//        claims.put(TokenEnum.AUTHORIZATION.getName(), authorities);
//
//        return claims;
//    }
//
//    private TokenDetails initTokenDetails() {
//        return TokenDetails.builder()
//                .accessToken(getAccessTokenValue())
//                .refreshToken(getRefreshTokenValue())
//                .build();
//    }
//    private MultiValueMap<String, HttpCookie> createCookies() {
//        MultiValueMap<String, HttpCookie> cookies = new LinkedMultiValueMap<>();
//        cookies.add(TokenEnum.ACCESS_TOKEN.getName(), new HttpCookie(TokenEnum.ACCESS_TOKEN.getName(), getAccessTokenValue()));
//        cookies.add(TokenEnum.REFRESH_TOKEN.getName(), new HttpCookie(TokenEnum.REFRESH_TOKEN.getName(), getRefreshTokenValue()));
//
//        return cookies;
//    }
//    private String getAccessTokenValue() { return "Access Toeken"; }
//    private String getRefreshTokenValue() { return "Refresh Token"; }
//}
