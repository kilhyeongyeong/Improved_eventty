package com.eventty.businessservice.event.application;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.eventty.businessservice.event.application.dto.request.ImageUploadRequestDTO;
import com.eventty.businessservice.event.domain.exception.UnsupportedContentTypeException;
import org.apache.commons.codec.binary.Base64;
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

    private final String absolutePath = System.getProperty("user.dir") + "/";
    private final String path = "image/";
    private AmazonS3 s3;

    public ImageUploadRequestDTO uploadFile(Long id, MultipartFile multipartFile) throws IOException{

        // 파일이 빈 것이 들어오면 빈 것을 반환
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        // 반환을 할 파일 리스트
        ImageUploadRequestDTO imageUploadRequestDTO = null;

        // 폴더 생성을 위한 현재 날짜 Get -> EX) user/currentDate/XXXXXX.jpeg
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = simpleDateFormat.format(new Date());

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String contentType = multipartFile.getContentType(); // jpeg, png, gif
        String originalFileExtension;

        if (ObjectUtils.isEmpty(contentType)) {
            throw UnsupportedContentTypeException.EXCEPTION;
            //throw new UnsupportedContentTypeException(contentType);
        }else{
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                originalFileExtension = ".gif";
            }else {
                throw UnsupportedContentTypeException.EXCEPTION;
                //throw new UnsupportedContentTypeException(contentType);
            }
        }

        // 파일에 새롭게 저장될 이름 -> 각 이름은 겹치면 안되므로 나노 초까지 동원하여 지정
        String newFileName = System.nanoTime() + originalFileExtension;

        // event/20230915/XXXXXXXX.jpg
        String storedFilePath = folderName + currentDate + "/" + newFileName;

        imageUploadRequestDTO = ImageUploadRequestDTO.builder()
                .eventId(id)
                .originalFileName(multipartFile.getOriginalFilename())
                .storedFilePath(storedFilePath)
                .fileSize(multipartFile.getSize())
                .build();

        // 파일 Local에 저장 -/ businessservice/image/XXXXXX.jpg
        String localPath = absolutePath + path + newFileName;
        file = new File(localPath);
        multipartFile.transferTo(file);

        // NCP Object Storage에 저장(로컬에 저장되어 있는 파일 -> NC Cloud에 저장하는 형식)
        uploadNCPStorage(storedFilePath, localPath);

        // Local에 저장되어 있는 파일 삭제
        File folder = new File(absolutePath + path);
        if(folder.exists()){
            FileUtils.cleanDirectory(folder);
            if(folder.isDirectory()) folder.delete();
        }

        return imageUploadRequestDTO;
    }

    public String getFileInfo(String filePath) throws IOException{
        // NCP ObjectStorage에서 갖고 오기
        S3ObjectInputStream inputStream = getNCPStorage(filePath);

        // 파일 읽기
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytesArray = new byte[12288];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(bytesArray)) != -1) {
            outputStream.write(bytesArray, 0, bytesRead);
        }

        // base64 인코딩
        // String result = new String(Base64.encodeBase64(outputStream.toByteArray()));
        String result = outputStream.toString();

        // stream 종료
        outputStream.close();
        inputStream.close();

        return result;
    }
    //******************************************************************************************************************
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
