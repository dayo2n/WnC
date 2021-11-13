package com.springweb.web.service.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.exception.file.UploadFileException;
import com.springweb.web.exception.file.UploadFileExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class S3Uploader implements FileService{

    private final AmazonS3Client amazonS3Client;
    private final String DIR_FORDER_NAME = "static";

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    @Override
    public String saveFile(MultipartFile file) throws UploadFileException, IOException, IOException {
        File uploadFile = convert(file)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));

        return upload(uploadFile, DIR_FORDER_NAME);
    }

    @Override
    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws UploadFileException, IOException {
        if (multipartFiles == null || multipartFiles.size()==0 || multipartFiles.get(0).isEmpty()) {
            return new ArrayList<>();
        }
        List<String> uploadedFilePathList = new ArrayList<>();

        try {
            for (MultipartFile multipartFile : multipartFiles) {
                uploadedFilePathList.add(saveFile(multipartFile));
            }

            List<UploadFile> uploadFiles = uploadedFilePathList.stream()
                    .map(uploadedFilePath -> UploadFile.createUploadFile(uploadedFilePath)).toList();

            return uploadFiles;
        } catch (Exception e) {
            e.getStackTrace();
            for (String filePath : uploadedFilePathList) {
                deleteFile(filePath);
            }
            throw new UploadFileException(UploadFileExceptionType.FILE_COULD_NOT_BE_SAVED);
        }
    }



    @Override
    public void deleteFile(String fullPath) {
        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, fullPath);
            //Delete
            amazonS3Client.deleteObject(deleteObjectRequest);

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFiles(List<UploadFile> uploadFiles) {
        uploadFiles.forEach(uploadFile -> deleteFile(uploadFile.getFilePath()));
    }



    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        targetFile.delete();
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름, static/dodooidqw.jpg
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return fileName;
    }
    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}
