package com.eventty.applyservice.application;

import com.eventty.applyservice.application.dto.response.FindEventInfoResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.List;

public interface ApiService {

    public void uriPathSetting(String host, String path);
    public void paramSetting(MultiValueMap<String, String> params);
    public List<FindEventInfoResponseDTO> apiRequest();
}
