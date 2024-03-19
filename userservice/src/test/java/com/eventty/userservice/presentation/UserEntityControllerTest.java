//package com.eventty.userservice.presentation;
//
//import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
//import com.eventty.userservice.application.UserService;
//import com.eventty.userservice.domain.code.ErrorCode;
//import com.eventty.userservice.presentation.exception.DataErrorLogger;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.MockBeans;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.io.FileInputStream;
//import java.time.LocalDate;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//@MockBeans(value = {
//        @MockBean(WebConfig.class),
//        @MockBean(BasicSecurityConfig.class),
//        @MockBean(UserService.class),
//        @MockBean(DataErrorLogger.class),
//        @MockBean(AuthenticationInterceptor.class)
//})
//public class UserEntityControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private String filePath;
//
//    @BeforeEach
//    public void setMockMvc() {
//        filePath = System.getProperty("user.dir") + "/src/test/java/com/eventty/userservice/testImage/choonsik.jpeg";
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    @DisplayName("[API][POST] 회원가입 성공")
//    public void postMeSuccessTest() throws Exception {
//        // Given
//        Long userId = 1L;
//        String name = "길동";
//        String address = "서울특별시 도봉구 도봉동 1";
//        LocalDate birth = LocalDate.of(1998, 06, 23);
//        String phone = "01012345678";
//        String url = "/api/users/me";
//
//        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
//                .builder()
//                .userId(userId)
//                .name(name)
//                .address(address)
//                .birth(birth)
//                .phone(phone)
//                .build();
//
//        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);
//
//        // When
//        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
//
//        // Then
//        response
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("SuccessResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("ErrorResponseDTO").doesNotHaveJsonPath());
//    }
//
//    @Test
//    @DisplayName("[API][POST] 회원가입 실패 - 필수값 부재")
//    public void postMeNullParameterTest() throws Exception {
//        // Given -- 전역변수로
//        Long userId = 1L;
//        String address = "서울특별시 도봉구 도봉동 1";
//        LocalDate birth = LocalDate.of(1998, 06, 23);
//        String phone = "01012345678";
//        String url = "/api/users/me";
//        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
//
//        UserCreateRequestDTO userCreateRequestDTO = UserCreateRequestDTO
//                .builder()
//                .userId(userId)
//                .address(address)
//                .birth(birth)
//                .phone(phone)
//                .build();
//
//        final String requestBody =objectMapper.writeValueAsString(userCreateRequestDTO);
//
//        // When
//        final ResultActions response = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody));
//
//        // Then
//        response
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("success").value(false))
//                .andExpect(jsonPath("errorResponseDTO").hasJsonPath())
//                .andExpect(jsonPath("successResponseDTO").doesNotHaveJsonPath());
//    }
//
//    @Test
//    @DisplayName("[API][GET] 내 정보 조회")
//    @WithMockCustomUser(authorities = {"USER"})
//    public void myInfoTest() throws Exception {
//        // Given
//        String url = "/users/me";
//
//        // When
//        final ResultActions response = mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON));
//
//        // Then
//        response
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("errorResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("successResponseDTO").hasJsonPath());
//    }
//
//    @Test
//    @DisplayName("[API][PATCH] 내 정보 수정")
//    @WithMockCustomUser(authorities = {"USER"})
//    public void patchMyInfoTest() throws Exception {
//        // Given -- 전역변수로
//        String name = "아항";
//        String address = "인천 남동구 장아산로 64 1, 2층";
//        String url = "/users/me";
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "choonsik.jpeg", "image/png", new FileInputStream(filePath));
//
//        final ResultActions response = mockMvc.perform(MockMvcRequestBuilders
//                        .multipart(HttpMethod.PATCH, url)
//                        .file(mockMultipartFile)
//                        .param("name", name)
//                        .param("address", address)
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        // Then
//        response.andExpect(status().isOk())
//                .andExpect(jsonPath("success").value(true))
//                .andExpect(jsonPath("SuccessResponseDTO").doesNotHaveJsonPath())
//                .andExpect(jsonPath("ErrorResponseDTO").doesNotHaveJsonPath());
//    }
//}