//package com.eventty.authservice.api;
//
//import com.eventty.authservice.api.utils.MakeUrlService;
//import com.eventty.authservice.global.response.ResponseDTO;
//import com.eventty.authservice.global.response.SuccessResponseDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.time.LocalDate;
//import java.util.Collections;
//
//import com.eventty.authservice.api.dto.request.UserCreateRequestDTO;
//import com.eventty.authservice.api.exception.ApiException;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//// Properties value를 Mockito를 실행할 때 못가져 오는 것도 그렇고,
//// 굳이 직접 서버의 주소를 가져다 쓰는 것은 안좋을 것 같아서 null로 고정
//@ExtendWith(MockitoExtension.class)
//public class ApiClientTest {
//
//    @InjectMocks
//    private ApiClient apiClient;
//
//    @Mock
//    private RestTemplate customRestTemplate;
//    @Mock
//    private MakeUrlService makeUrlService;
//
//    @Test
//    @DisplayName("회원 가입 요청 API 성공")
//    public void createUserApi_SUCCESS() {
//
//        // Given
//        UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO();
//        ResponseDTO<Void> responseDTO = ResponseDTO.of(SuccessResponseDTO.of(null));
//        ResponseEntity<ResponseDTO<Void>> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
//        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
//
//        // When
//        when(makeUrlService.createUserUri()).thenReturn(URI.create(""));
//        when(customRestTemplate.exchange(any(URI.class), eq(HttpMethod.POST), httpEntityCaptor.capture(), eq(new ParameterizedTypeReference<ResponseDTO<Void>>() {})))
//                .thenReturn(responseEntity);
//
//        // Then
//        assertEquals(apiClient.createUserApi(userCreateRequestDTO), responseEntity);
//
//        // Verify
//        HttpEntity<UserCreateRequestDTO> capturedEntity = httpEntityCaptor.getValue();
//        assertEquals(userCreateRequestDTO, capturedEntity.getBody());
//    }
//
//    @Test
//    @DisplayName("[POST] 회원 가입 요청 실패 응답")
//    public void createUserApi_FAIL() {
//        // Given
//        UserCreateRequestDTO userCreateRequestDTO = createUserCreateRequestDTO();
//        ArgumentCaptor<HttpEntity> httpEntityCaptor = ArgumentCaptor.forClass(HttpEntity.class);
//
//        // When
//        when(makeUrlService.createUserUri()).thenReturn(URI.create(""));
//        doThrow(ApiException.class).when(customRestTemplate)
//                .exchange(any(URI.class), eq(HttpMethod.POST), httpEntityCaptor.capture(), eq(new ParameterizedTypeReference<ResponseDTO<Void>>() {}));
//
//
//        // Then
//        assertThrows(ApiException.class, () -> apiClient.createUserApi(userCreateRequestDTO));
//    }
//
//    private UserCreateRequestDTO createUserCreateRequestDTO() {
//        return UserCreateRequestDTO.builder()
//                .userId(1L)
//                .name("eventty0")
//                .address("강원도 인제군")
//                .birth(LocalDate.now())
//                .phone("000-0000-0000")
//                .build();
//    }
//
//    private <T> HttpEntity<T> createHttpPostEntity(T dto) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        //headers.add("X-CSRF-TOKEN", "value");
//
//        return new HttpEntity<>(dto, headers);
//    }
//
//
//}
