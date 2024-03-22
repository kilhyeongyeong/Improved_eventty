package com.eventty.applyservice.presentation;

import com.eventty.applyservice.domain.annotation.ApiErrorCode;
import com.eventty.applyservice.domain.annotation.ApiSuccessData;
import com.eventty.applyservice.domain.code.ErrorCode;
import com.eventty.applyservice.presentation.dto.ErrorResponseDTO;
import com.eventty.applyservice.presentation.dto.ResponseDTO;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    // 제목 옆에 조그맣게 표시될 버전 정보
    private final String VERSION = "v0.0.1";

    // 제목
    private final String TITLE = "\"Apply SERVER API 명세서\"";

    // 제목 밑에 들어갈 설명
    private final String DESCRIPTION = "EVENTTY - Apply SERVER API 명세서 입니다. \n행사 신청에 관련된 정보만 담겨 있습니다.";

    // HTTP Success Code
    private final String[] httpSuccessCodes = {"200", "201"};

    // Apiresponse가 있을 경우 매핑될 필드
    private static ApiResponse apiResponse = null;
    private static String state = null;

    private io.swagger.v3.oas.annotations.media.Content[] contents = {};

    /**
     * Swagger API 설정
     * @param
     * @return OpenAPI
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo())
                .paths(new Paths());
    }

    // Swagger title, version등 화면에 출력될 기본정보 setting
    private Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/kilhyeongyeong/Improved_eventty");
        license.setName("eventty");

        return new Info()
                .version(VERSION)
                .title(TITLE)
                .description(DESCRIPTION)
                .license(license);
    }
    // ----------------------------------------------------------------

    /**
     * 커스텀 어노테이션 적용
     */
    @Bean
    public OperationCustomizer customOperationCutomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            // 커스텀 어노테이션 값 불러오기
            ApiErrorCode apiErrorCode = handlerMethod.getMethodAnnotation(ApiErrorCode.class);
            ApiSuccessData apiSuccessData = handlerMethod.getMethodAnnotation(ApiSuccessData.class);

            io.swagger.v3.oas.annotations.media.Schema schema = apiSuccessData.schema();


            //전처리
            preprocessing(operation);

            if(apiSuccessData != null){
                generateSucessResponseDoc(apiSuccessData);
            }

            if(apiResponse != null){
                generateSucessResponseDoc(operation);
            }


            // @ApiErrorCode가 있을 경우 실행
            if (apiErrorCode != null) {
                generateErrorCodeResponseDoc(operation, apiErrorCode.value());
            }

            return operation;
        };
    }

    private void generateSucessResponseDoc(ApiSuccessData apiSuccessData){

    }
    
    private void preprocessing(Operation operation){
        
        // Success response를 위한 전처리
        ApiResponses apiResponses = operation.getResponses();
        for(String key : httpSuccessCodes){
            if(apiResponses.get(key) != null){
                state = key;
                apiResponse = apiResponses.get(key);
                break;
            }
        }
        
        // Delete도 Custom을 사용하기 때문에 전체 삭제
        operation.setResponses(new ApiResponses());
    }

    /**
     * 기본 Annotation 기반의 Success Response 문서화
     * @param operation
     * @param <T>
     */
    private <T> void generateSucessResponseDoc(Operation operation/*, ApiSuccessData apiSuccessData*/){
        ApiResponses responses = operation.getResponses();
        ApiResponse response = new ApiResponse();

        Content content = new Content();
        MediaType mediaType = new MediaType();

        Schema isSuccessSchema = new Schema();
        isSuccessSchema.setType("boolean");

        Map<String, Schema> successResponseProperties = new HashMap<>();
        Map<String, Schema> responseProperties = new HashMap<>();

        try{
//            Schema testSchema1 = new Schema();
//            testSchema1.$ref("#components/schemas/FindUsingTicketResponseDTO");
//
//            Schema testSchema2 = new Schema();
//            testSchema2.$ref("#schemas/FindUsingTicketResponseDTO");

            successResponseProperties.put("data", apiResponse.getContent().get("application/json").getSchema());
//            successResponseProperties.put("data", testSchema1);
//            successResponseProperties.put("data", testSchema2);
            Schema sucessResponseSchema = new Schema();
            sucessResponseSchema.properties(successResponseProperties);
            responseProperties.put("SuccessResponseDTO", sucessResponseSchema);
        }catch (Exception e){
            // 건너뛰기
        }

        responseProperties.put("isSuccess", isSuccessSchema);

        Schema responseSchema = new Schema();
        responseSchema.properties(responseProperties);

        mediaType.schema(responseSchema);

        response.setDescription(apiResponse.getDescription());
        response.setContent(content.addMediaType("application/json", mediaType));

        responses.addApiResponse(state, response);
        operation.setResponses(responses);
    }

    /**
     * ErrorCode(EnumClass)를 기반으로한 오류 응답 문서화
     * @param operation
     * @param errorCodes
     * @param <T>
     */
    private <T> void generateErrorCodeResponseDoc(Operation operation, ErrorCode[] errorCodes) {
        ApiResponses responses = operation.getResponses();

        for(ErrorCode errorCode : errorCodes){
            ApiResponse apiResponse = Optional.ofNullable(responses.get(String.valueOf(errorCode.getStatus()))).orElseGet(ApiResponse::new);
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
