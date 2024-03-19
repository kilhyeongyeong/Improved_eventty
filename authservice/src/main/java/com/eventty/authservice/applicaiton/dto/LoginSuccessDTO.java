package com.eventty.authservice.applicaiton.dto;

import com.eventty.authservice.presentation.dto.response.LoginResponseDTO;

public record LoginSuccessDTO (
        SessionTokensDTO sessionTokensDTO,
        LoginResponseDTO loginResponseDTO,
        String csrfToken
){
}
