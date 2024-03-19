package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
import com.eventty.applyservice.domain.code.ServerUri;
import com.eventty.applyservice.presentation.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService{

    private final RestTemplate customRestTemplate;
    private MultiValueMap<String, String> params;
    private String uri;

    @Override
    public void uriPathSetting(String host, String path){
        uri = host.concat(path);
    }

    @Override
    public void paramSetting(MultiValueMap<String, String> params){
        this.params = params;
    }

    @Override
    public List<FindEventInfoResponseDTO> apiRequest() {
        log.info("API 호출 From: {} To: {} Purpose: {}", "Apply Server", "Event Server", "Find Events List");
        ResponseEntity<ResponseDTO<List<FindEventInfoResponseDTO>>> response = null;
        try{
            response =  customRestTemplate.exchange(
                    makeUri(),
                    HttpMethod.GET,
                    createHttpPostEntity(null),
                    new ParameterizedTypeReference<ResponseDTO<List<FindEventInfoResponseDTO>>>() {}
            );
        }catch(Exception e){
            log.error(e.getMessage());
        }

        if(response.getBody() == null || response.getBody().getErrorResponseDTO() != null){
            log.error("ApplyServer - response is null or getErrorResponse : {}", response);
            return null;
        }

        log.debug("Resposne : {}", response);
        if(response.getBody() != null && response.getBody().getSuccessResponseDTO() != null){
            for(FindEventInfoResponseDTO responseDTO : response.getBody().getSuccessResponseDTO().getData()){
                log.debug("ResponseDTO : {}", responseDTO);
            }
        }else {
            log.error("Response Error!!!!!");
        }

        return response.getBody().getSuccessResponseDTO().getData();
    }

    private URI makeUri() {
        return UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParams(params)
                .build()
                .encode()
                .toUri();
    }

    private  <T> HttpEntity<T> createHttpPostEntity(T dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("X-Requires-Auth", "True");

        return new HttpEntity<>(dto, headers);
    }
}
