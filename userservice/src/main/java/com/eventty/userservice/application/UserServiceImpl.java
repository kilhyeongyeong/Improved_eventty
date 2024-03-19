package com.eventty.userservice.application;

import com.eventty.userservice.application.dto.*;
import com.eventty.userservice.application.dto.request.*;
import com.eventty.userservice.application.dto.response.*;
import com.eventty.userservice.domain.UserEntity;
import com.eventty.userservice.domain.UserImageEntity;
import com.eventty.userservice.domain.UserImageJPARepository;
import com.eventty.userservice.domain.exception.DuplicateUserIdException;
import com.eventty.userservice.domain.exception.UserInfoNotFoundException;
import com.eventty.userservice.domain.UserJPARepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserJPARepository userJPARepository;
    private final UserImageJPARepository userImageJPARepository;
    private final FileHandler fileHandler;
    private final EntityManager em;

    @Transactional
    public UserEntity signUp(UserCreateRequestDTO userCreateRequestDTO){
        duplicateUserExceptionCheck(userCreateRequestDTO);
        return userJPARepository.save(userCreateRequestDTO.toEntity());
    }

    @Transactional
    public UserSaveImageResponseDTO oauthSignUp(UserOAuthCreateRequestDTO userOAuthCreateRequestDTO) {
        UserEntity user = userJPARepository.findById(userOAuthCreateRequestDTO.getUserId()).orElse(null);

        Long imageId = user == null ? null : user.getImageId();
        if(userOAuthCreateRequestDTO.getPicture() != null && !"".equalsIgnoreCase(userOAuthCreateRequestDTO.getPicture().trim()))
            imageId = userImageJPARepository.save(userOAuthCreateRequestDTO.toUserImageEntity()).getId();

        user = userJPARepository.save(userOAuthCreateRequestDTO.toUserEntity(imageId));

        UserImageEntity userImage = user.getImageId() == null ? null : userImageJPARepository.findById(user.getImageId()).orElse(null);

        if(userImage == null || userImage.getStoredFilePath() == null || "".equalsIgnoreCase(userImage.getStoredFilePath().trim())) return null;
        return UserSaveImageResponseDTO
                .builder()
                .imagePath(userImage.getStoredFilePath())
                .build();
    }

    @Transactional
    public UserFindByIdResponseDTO getMyInfo(Long id){
        UserEntity user = findUserByEMAndDB(id);

        UserImageEntity userImage = null;
        if(user.getImageId() != null)
            userImage = findUserImageByEMAndDB(user.getImageId());

        return new UserFindByIdResponseDTO(user, userImage);
    }

    @Transactional
    public UserUpdateImageResponseDTO updateMyInfo(Long userId,
                                   UserUpdateRequestDTO userUpdateRequestDTO,
                                   UserImageUpdateRequestDTO userImageUpdateRequestDTO){
        // 초기화
        UserUpdateRequestDTO userUpdate = new UserUpdateRequestDTO(userUpdateRequestDTO);
        UserImageUpdateRequestDTO userImageUpdate = new UserImageUpdateRequestDTO(userImageUpdateRequestDTO);
        UserImageEntity userImageEntity = null;

        // 파일 관련 logic
        UserImageDTO userImageDTO = fileUpload(userId, userImageUpdate);

        Long imageId = userImageDTO.getId();
        if(imageId != null && imageId != 0){
            imageId = (imageId == -1) ? 0L : imageId;
        }else{
            userImageEntity = userImageJPARepository.save(userImageDTO.toEntity());
            imageId = userImageEntity.getId();
        }

        // 회원 정보 저장
        userJPARepository.save(
                new UserUpdateDTO(findUserByEMAndDB(userId))
                        .toEntity(userUpdate, imageId));

        if(userImageEntity == null && imageId == 0L) return null;
        else if(userImageEntity != null) return new UserUpdateImageResponseDTO(userImageEntity.getStoredFilePath(), userImageEntity.getOriginalFileName(), imageId);
        else return new UserUpdateImageResponseDTO(imageId);
    }

    @Override
    public HostFindByIdResposneDTO apiGetHostInfo(Long hostId) {
        UserEntity host = findUserByEMAndDB(hostId);
        log.debug("Return : {}", host);
        return new HostFindByIdResposneDTO(host);
    }

    @Override
    public UserImageFindByIdResponseDTO apiGetUserImage(Long userId) {
        UserEntity user = findUserByEMAndDB(userId);
        log.debug("Return : {}", user);
        UserImageEntity userImage = null;

        if(user.getImageId() == null)
            return null;

        userImage = findUserImageByEMAndDB(user.getImageId());
        log.debug("Return : {}", userImage);
        return new UserImageFindByIdResponseDTO(userImage);
    }

    @Override
    public List<Long> apiCheckUserInfo(UserCheckRequestDTO userCheckRequestDTO) {
        List<UserEntity> users = userJPARepository.findByNameAndPhone(userCheckRequestDTO.getName(), userCheckRequestDTO.getPhone());
        log.debug("Return : {}", users);
        if(users.size() == 0 || users.isEmpty())
            return null;

        List<Long> userIds = new ArrayList<>();
        for(UserEntity user: users){
            userIds.add(user.getUserId());
        }
        return userIds;
    }
    // *****************************************************************************************************************
    /**
     * 파일 관련 기능
     */
    private UserImageDTO fileUpload(Long userId, UserImageUpdateRequestDTO userImageUpdateRequestDTO){

        MultipartFile multipartFile = userImageUpdateRequestDTO.getImage();

        // 파일이 비었을 경우 & update가 일어났을 경우
        // 사진은 있되 파일 업로드로 버튼을 잘못 누른경우
        if((multipartFile == null || multipartFile.isEmpty()) && !"".equalsIgnoreCase(userImageUpdateRequestDTO.getImageId())){
            return UserImageDTO.builder().id(Long.parseLong(userImageUpdateRequestDTO.getImageId())).build();
        }

        // 파일이 비었을 경우
        if (multipartFile == null || multipartFile.isEmpty())
            return UserImageDTO.builder().id(-1L).build();

        // 파일 정보 parsing
        UserImageDTO userImageDTO = fileHandler.parseFileInfo(userId, userImageUpdateRequestDTO.getImage());

        // 파일 저장
        try{
            fileHandler.saveFileInfo(userImageDTO, userImageUpdateRequestDTO.getImage());
        }catch (IOException e){
            log.error("fileUpload error occured : {}", e.getMessage());
        }

        // 저장
        return userImageDTO;
    }

    // *****************************************************************************************************************
    /**
     * User 관련 기능
     */
    private UserEntity findUserByEMAndDB(Long id){
        return Optional.ofNullable(
                em.find(UserEntity.class, id)
        ).orElseThrow(
                () -> new UserInfoNotFoundException(id)
        );
    }

    private UserImageEntity findUserImageByEMAndDB(Long id){
        return Optional.ofNullable(
                em.find(UserImageEntity.class, id)
        ).orElseThrow(
                () -> new UserInfoNotFoundException(id)
        );
    }

    // userId 중복 검사
    private void duplicateUserExceptionCheck(UserCreateRequestDTO userCreateRequestDTO){
        Optional.ofNullable(
                em.find(UserEntity.class, userCreateRequestDTO.getUserId())
        ).ifPresent(
                entity -> {
                    throw new DuplicateUserIdException(entity);
                }
        );
    }
}
