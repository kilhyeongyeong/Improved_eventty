package com.eventty.authservice.infrastructure.config;

import com.eventty.authservice.global.Enum.ErrorCode;
import com.eventty.authservice.global.response.ErrorResponseDTO;
import com.eventty.authservice.global.response.ResponseDTO;
import com.eventty.authservice.global.response.SuccessResponseDTO;
import com.eventty.authservice.infrastructure.annotation.ApiErrorCode;
import com.eventty.authservice.infrastructure.annotation.ApiSuccessData;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    // 버전 정보
    private final String VERSION = "v0.0.1";

    // 제목
    private final String TITILE = "\"AUTH SERVER API 명세서 \"";

    // 설명
    private final String DESCRIPTION = "AUTH - AUTH SERVER API 명세서 입니다.\n" +
            "인증 및 회원에 관련된 요청을 처리합니다.";

    /**
     * Swagger API 설정
     * @param
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo());
    }

    // Swagger Title, Version 등 화면에 출력할 기본 정보 Setting
    private Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/jeongbeomSeo/eventty");
        license.setName("eventty");

        return new Info()
                .version(VERSION)
                .title(TITILE)
                .description(DESCRIPTION)
                .license(license);
    }
    // ----------------------------------------------------------------
    /**
     * 커스텀 어노테이션 적용
     */
    @Bean
    public OperationCustomizer customOperationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            // 커스텀 어노테이션 값
            ApiErrorCode apiErrorCode = handlerMethod.getMethodAnnotation(ApiErrorCode.class);
            ApiSuccessData apiSuccessData = handlerMethod.getMethodAnnotation(ApiSuccessData.class);

            // 전처리
            operation.setResponses(new ApiResponses());

            // @ApiSuccessData가 있을 경우 실행
            if (apiSuccessData != null) {
                generateSuccesssResponseDoc(operation, apiSuccessData);
            }

            // @ApiErrorCode가 있을 경우 실행
            if (apiErrorCode != null) {
                generateErrorCodeResponseDoc(operation, apiErrorCode.value());
            }

            return operation;
        };
    }

    /**
     * DTO.class를 기반으로한 성공 응답 예시 문서화
     * @param operation
     * @param apiSuccessData
     * @param <T>
     */
    private <T> void generateSuccesssResponseDoc(Operation operation, ApiSuccessData apiSuccessData) {
        Class<?> responseDTO = apiSuccessData.value();
        String status = apiSuccessData.stateCode();

        //-----------------------------------------------------generateInstance
        ApiResponses responses = operation.getResponses();
        ApiResponse response = new ApiResponse();
        Content content = new Content();
        MediaType mediaType = new MediaType();
        Example successExample = new Example();

        //-----------------------------------------------------settingInstance
        try {
            if (apiSuccessData.isArray()) {
                successExample.setValue(
                        ResponseDTO.of(
                                SuccessResponseDTO.of(
                                        List.of(responseDTO.getConstructor().newInstance()))));
            } else {
                successExample.setValue(
                        ResponseDTO.of(
                                SuccessResponseDTO.of(
                                        responseDTO.getConstructor().newInstance())));
            }
        } catch (Exception e) {
            successExample.setValue(new ResponseDTO<>());
        }

        mediaType.addExamples("ResponseDTO", successExample);

        response.setDescription(HttpStatus.valueOf(Integer.parseInt(status)).toString());
        response.setContent(content.addMediaType("application/json", mediaType));

        responses.addApiResponse(status, response);
        operation.setResponses(responses);
    }

    /**
     * ErrorCode(Enum Class)를 기반으로한 오류 응답 문서화
     * @param operation
     * @param errorCodes
     * @param <T>
     */
    private <T> void generateErrorCodeResponseDoc(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        for (ErrorCode errorCode : errorCodes) {
            ApiResponse apiResponse = Optional.ofNullable(
                    responses.get(String.valueOf(
                            errorCode.getStatus()))).orElseGet(ApiResponse::new);
            Content content = Optional.ofNullable(apiResponse.getContent()).orElseGet(Content::new);
            MediaType mediaType = content.getOrDefault("application/json", new MediaType());

            Example example = new Example();
            example.setDescription(errorCode.getMessage());
            example.setValue(ResponseDTO.of(ErrorResponseDTO.of(errorCode)));
            mediaType.addExamples(errorCode.name(), example);

            apiResponse.setDescription(HttpStatus.valueOf(errorCode.getStatus()).toString());
            apiResponse.setContent(content.addMediaType("application/json", mediaType));

            responses.addApiResponse(String.valueOf(errorCode.getStatus()), apiResponse);
        }
    }
}
