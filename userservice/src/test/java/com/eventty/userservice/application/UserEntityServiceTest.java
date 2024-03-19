//package com.eventty.userservice.application;
//
//import com.eventty.userservice.application.dto.request.UserCreateRequestDTO;
//import com.eventty.userservice.application.dto.request.UserImageUpdateRequestDTO;
//import com.eventty.userservice.application.dto.response.UserFindByIdResponseDTO;
//import com.eventty.userservice.application.dto.request.UserUpdateRequestDTO;
//import com.eventty.userservice.domain.UserEntity;
//import com.eventty.userservice.domain.UserImageEntity;
//import com.eventty.userservice.domain.exception.DuplicateUserIdException;
//import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class UserEntityServiceTest {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    EntityManager em;
//
//    @Test
//    @DisplayName("[Success] 회원가입")
//    @Transactional
//    public void createUserTest(){
//        // Given
//        Long userId = 1000L;
//        String name = "홍박사";
//        String address = "서울특별시 강남구 테헤란로";
//        LocalDate birth = LocalDate.of(1988, 5, 3);
//        String phone = "01045628526";
//
//        UserCreateRequestDTO request = UserCreateRequestDTO
//                                                    .builder()
//                                                    .userId(userId)
//                                                    .name(name)
//                                                    .address(address)
//                                                    .birth(birth)
//                                                    .phone(phone)
//                                                    .build();
//
//        // When
//        UserEntity userEntity = userService.createUser(request);
//
//        // Then
//        assertEquals(userEntity.getUserId(), userId);
//        assertEquals(userEntity.getName(), name);
//        assertEquals(userEntity.getAddress(), address);
//        assertEquals(userEntity.getBirth(), birth);
//        assertEquals(userEntity.getPhone(), phone);
//    }
//
//    @Test
//    @DisplayName("[Fail] 회원가입시 userId(PK)값 존재")
//    @Transactional
//    public void createUserFailTest(){
//        // Given
//        Long userId = 1L;
//        String name = "홍박사";
//        String address = "서울특별시 강남구 테헤란로";
//        LocalDate birth = LocalDate.of(1988, 5, 3);
//        String phone = "01045628526";
//
//        UserCreateRequestDTO request = UserCreateRequestDTO
//                .builder()
//                .userId(userId)
//                .name(name)
//                .address(address)
//                .birth(birth)
//                .phone(phone)
//                .build();
//
//        em.persist(request.toEntity());
//
//        // When, Then
//        assertThrows(DuplicateUserIdException.class, () -> userService.createUser(request));
//
//    }
//
//    @Test
//    @DisplayName("[Success] 내 정보 조회 - image제외")
//    @Transactional
//    public void userFindByIdTest(){
//        // Given
//        Long userId = 100L;
//        String name = "홍박사";
//        String address = "서울특별시 강남구 테헤란로";
//        LocalDate birth = LocalDate.of(1988, 5, 3);
//        String phone = "01045628526";
//
//        UserEntity user = UserEntity
//                .builder()
//                .userId(userId)
//                .name(name)
//                .address(address)
//                .birth(birth)
//                .phone(phone)
//                .build();
//
//        em.persist(user);
//
//        // When
//        UserFindByIdResponseDTO response =  userService.findUserById(userId);
//
//        // Then
//        assertEquals(response.getUserId(), userId);
//        assertEquals(response.getName(), name);
//        assertEquals(response.getAddress(), address);
//        assertEquals(response.getBirth(), birth);
//        assertEquals(response.getPhone(), phone);
//    }
//
//    @Test
//    @DisplayName("[Fail][UserInfoNotFoundException] 내 정보 조회")
//    @Transactional
//    public void userInfoNotFoundExceptionTest(){
//        // Given
//        Long userId = 1000000000L;
//
//        // When, Then
//        assertThrows(UserInfoNotFoundException.class, () -> userService.findUserById(userId));
//    }
//
//    @Test
//    @DisplayName("[Success] 내정보 수정")
//    @Transactional
//    public void userUpdateTest() throws Exception{
//        // Given
//        Long userId = 100L;
//        String name = "홍박사";
//        LocalDate birth = LocalDate.of(1988, 5, 3);
//        String phone = "01045628526";
//        String address = "주소주소";
//
//
//        UserEntity user = UserEntity
//                .builder()
//                .userId(userId)
//                .name(name)
//                .address(address)
//                .birth(birth)
//                .phone(phone)
//                .build();
//
//        em.persist(user);
//
//        String addressParam = "인천광역시 남동구";     // 값 -> new  값
//        String birthParam = "";                     // 값 -> null 값
//
//        UserUpdateRequestDTO updateRequestDTO = UserUpdateRequestDTO
//                .builder()
//                .address(addressParam)
//                .birth(birthParam)
//                .build();
//
//        // file
//        String filePath = System.getProperty("user.dir") + "/src/test/java/com/eventty/userservice/testImage/choonsik.jpeg";     // null -> new 값
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "choonsik.jpeg", "image/png", new FileInputStream(filePath));
//        UserImageUpdateRequestDTO userImageUpdateRequestDTO = new UserImageUpdateRequestDTO(null, mockMultipartFile);
//
//        // When
//        UserEntity userEntity =  userService.updateUser(userId, updateRequestDTO, userImageUpdateRequestDTO);
//
//        // Then
//        assertNotNull(em.find(UserEntity.class, userId).getImageId());
//        assertEquals(addressParam, em.find(UserEntity.class, userId).getAddress());
//        assertNull(em.find(UserEntity.class, userId).getBirth());
//    }
//}
