package com.eventty.userservice.application;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.eventty.userservice.application.dto.UserImageDTO;
import com.eventty.userservice.domain.exception.UnsupportedContentTypeException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileHandler {
    @Value("${ncp.endPoint}")
    public String endPoint;
    @Value("${ncp.regionName}")
    public String regionName;
    @Value("${ncp.accessKey}")
    public String accessKey;
    @Value("${ncp.secretKey}")
    public String secretKey;
    @Value("${ncp.bucketName}")
    public String bucketName;
    @Value("${ncp.folderName}")
    public String folderName;

    private final String absolutePath = System.getProperty("user.dir") + "\\";
    private final String path = "image\\";
    private AmazonS3 s3;

    public UserImageDTO parseFileInfo(Long userId, MultipartFile multipartFile){
        // 폴더 생성을 위한 현재 날짜 Get -> EX) user/currentDate/XXXXXX.jpeg
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = simpleDateFormat.format(new Date());

        String contentType = multipartFile.getContentType(); // jpeg, png, gif
        String originalFileExtension;

        if (ObjectUtils.isEmpty(contentType)) {
            throw new UnsupportedContentTypeException(contentType);
        }else{
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                originalFileExtension = ".gif";
            }else {
                throw new UnsupportedContentTypeException(contentType);
            }
        }

        // 파일에 새롭게 저장될 이름 -> 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
        String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;

        // user/20230915/XXXXXXXX.jpg
        String storedFilePath = folderName + currentDate + "/" + newFileName;

        UserImageDTO userImageDTO = UserImageDTO.builder()
                .userId(userId)
                .originalFileName(multipartFile.getOriginalFilename())
                .storedFilePath(storedFilePath)
                .fileSize(multipartFile.getSize())
                .build();

        return userImageDTO;
    }

    public void saveFileInfo(UserImageDTO userImage, MultipartFile multipartFile) throws IOException{
        // Local에 File 저장
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String localFilePath = absolutePath + path + userImage.getOriginalFileName();

        file = new File(localFilePath);
        multipartFile.transferTo(file);

        // NCP Object Storage에 저장(로컬에 저장되어 있는 파일 -> NC Cloud에 저장하는 형식)
        uploadNCPStorage(userImage.getStoredFilePath(), localFilePath);

        // Local에 저장되어 있는 파일 삭제
        File folder = new File(absolutePath + path);
        if(folder.exists()){
            FileUtils.cleanDirectory(folder);
            if(folder.isDirectory()) folder.delete();
        }
    }

    /**
     * NCP 관련 메서드
     */
    public void s3Init(){
        // S3 Client
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint,regionName))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    private void uploadNCPStorage(String filePath, String path){
        // NCP setting
        s3Init();

        s3.putObject(bucketName, filePath, new File(path));
        System.out.format("Object %s has been created.\n", filePath);

        // 권한 부여
        AccessControlList accessControlList = s3.getObjectAcl(bucketName, filePath);
        accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        s3.setObjectAcl(bucketName, filePath, accessControlList);
    }

    private S3ObjectInputStream getNCPStorage(String filePath){
        s3Init();

        S3Object s3Object = s3.getObject(bucketName, filePath);
        return s3Object.getObjectContent();
    }
}
