package com.eventty.businessservice.event.api;

import com.eventty.businessservice.event.api.dto.response.QueryAppliesCountResponseDTO;
import com.eventty.businessservice.event.api.dto.response.HostFindByIdResponseDTO;
import com.eventty.businessservice.event.api.utils.MakeUrlService;
import com.eventty.businessservice.event.presentation.response.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ApiClient {
    private static final String AUTH_HEADER = "X-Requires-Auth";

    // @Bean에 이름을 지정하지 않아서 생성자 이름을 따라감
    private final MakeUrlService makeUrlService;

    private final RestTemplate basicRestTemplate;

    private final RestTemplate customRestTemplate;

    public ResponseEntity<ResponseDTO<HostFindByIdResponseDTO>> queryUserInfoApi(Long hostId) {

        HttpEntity<Void> entity = createHttpEntity(null);

        // hostId request Parameter로 담아주는 작업 추가
        URI uri = makeUrlService.queryHostInfo(hostId);

        // API 호출은 Loggin Level을 Info로 지정해서 로그 관리
        logApiCall("Event server", "User server", "Query user info");
        return customRestTemplate.exchange(
                uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDTO<HostFindByIdResponseDTO>>() {}
        );
    }

    public ResponseEntity<ResponseDTO<List<QueryAppliesCountResponseDTO>>> queryAppliesCountApi(Long eventId) {

        HttpEntity<Void> entity = createHttpEntity(null);

        URI uri = makeUrlService.queryTicketCount(eventId);

        logApiCall("Event server", "Apply server", "Query applies Count");

        return customRestTemplate.exchange(
                uri, HttpMethod.GET, entity, new ParameterizedTypeReference<ResponseDTO<List<QueryAppliesCountResponseDTO>>>() {}
        );
    }

    private void logApiCall(String from, String to, String purpose) {
        log.info("API 호출 From: {} To: {} Purpose: {}", from, to, purpose);
    }

    // 요청 보낼 때 자신의 id가 필요하지 않는 경우 (로그인이 필요하지 않는 경우)
    private <T> HttpEntity<T> createHttpEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(dto, headers);
    }

    // 요청 보내는 본인의 id가 필요한 요청인 경우 (로그인이 필요한 경우)=> UserContextInterceptor가 작업 수행
    private <T> HttpEntity<T> createAuthenticateHttpEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(AUTH_HEADER, "True");
        return new HttpEntity<>(dto, headers);
    }
}
