//package com.eventty.gateway.api;
//
//import com.eventty.gateway.api.utils.MakeUrlService;
//import com.eventty.gateway.global.dto.ResponseDTO;
//import com.eventty.gateway.global.dto.SuccessResponseDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ApiClientTest {
//
//    @InjectMocks
//    private ApiClient apiClient;
//
//    @Mock
//    private RestTemplate customRestTemplate;
//
//    @Mock
//    private MakeUrlService makeUrlService;
//
//    @Test
//    @DisplayName("새로운 토큰 받아오기 성공")
//    public void getNewTokens_SUCCESS() {
//
//        // Given
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO();
//        NewTokensResponseDTO newTokensResponseDTO = createNewTokensResponseDTO();
//        ResponseDTO<NewTokensResponseDTO> responseDTO = ResponseDTO.of(SuccessResponseDTO.of(newTokensResponseDTO));
//        ResponseEntity<ResponseDTO<NewTokensResponseDTO>> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.OK);
//        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
//
//        // When
//        when(makeUrlService.createNewTokenUri()).thenReturn(URI.create(""));
//        when(customRestTemplate.exchange(
//                any(URI.class), eq(HttpMethod.POST), httpEntityCaptor.capture(), eq(new ParameterizedTypeReference<ResponseDTO<NewTokensResponseDTO>>() {})))
//                .thenReturn(responseEntity);
//
//        // Then
//        assertEquals(apiClient.getNewTokens(getNewTokensRequestDTO), responseEntity);
//
//        // verify
//        HttpEntity<GetNewTokensRequestDTO> capturedEntity = httpEntityCaptor.getValue();
//        assertEquals(getNewTokensRequestDTO, capturedEntity.getBody());
//    }
//
//    @Test
//    @DisplayName("새로운 토큰 받아오기 실패 응답 - FAIL_GET_NEW_TOKENS_EXCEPTION")
//    public void getNewTokens_FAIL() {
//        // Given
//        GetNewTokensRequestDTO getNewTokensRequestDTO = createGetNewTokensRequestDTO();
//        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
//
//        // When
//        when(makeUrlService.createNewTokenUri()).thenReturn(URI.create(""));
//        doThrow(FailGetNewTokensException.class).when(customRestTemplate)
//                .exchange(any(URI.class), eq(HttpMethod.POST), httpEntityCaptor.capture(), eq(new ParameterizedTypeReference<ResponseDTO<NewTokensResponseDTO>>() {}));
//
//
//        // Then
//        assertThrows(FailGetNewTokensException.class, () -> apiClient.getNewTokens(getNewTokensRequestDTO));
//    }
//
//    private GetNewTokensRequestDTO createGetNewTokensRequestDTO() {
//        return GetNewTokensRequestDTO.builder()
//                .userId(1L)
//                .refreshToken("Old_Refresh_Token")
//                .build();
//    }
//
//    private NewTokensResponseDTO createNewTokensResponseDTO() {
//        return NewTokensResponseDTO.builder()
//                .accessToken("New_Access_Token")
//                .refreshToken("New_Refresh_Token")
//                .build();
//    }
//}
