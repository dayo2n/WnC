package com.springweb.web.service.file;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.exception.file.UploadFileException;
import com.springweb.web.exception.file.UploadFileExceptionType;
import com.springweb.web.repository.file.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("${file.dir}")//파일이 저장될 경로 => 도메인 어찌고 저쩌고 할거임
    private String fileDir;


    //UUID.확장자 로 파일 이름 만들기
    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    //확장자 가져오기
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }


    @Override
    public String saveFile(MultipartFile file) throws UploadFileException, IOException {
        log.info("파일 저장 시작");
        if (file.isEmpty()) {
            throw new UploadFileException(UploadFileExceptionType.FILE_COULD_NOT_BE_SAVED);
        }//check file exist

        String originalFilename = file.getOriginalFilename();//image.jpg
        String storeFileName = createStoreFileName(originalFilename);//UUID.jpg(확장자)

        file.transferTo(new File(getFullPath(storeFileName)));//save file in computer

        log.info("파일 저장에 성공했습니다 저장위치:[{}]",storeFileName);
        return getFullPath(storeFileName);
    }

    @Trace
    @Override
    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws UploadFileException, IOException {
        if (multipartFiles == null || multipartFiles.get(0).isEmpty()) {
            return new ArrayList<>();
        }
        log.info("파일 저장을 실햅합니다.");
        List<String> uploadedFilePathList = new ArrayList<>();

        try {
            for (MultipartFile multipartFile : multipartFiles) {
                uploadedFilePathList.add(saveFile(multipartFile));
            }

            List<UploadFile> uploadFiles = uploadedFilePathList.stream()
                    .map(uploadedFilePath -> UploadFile.createUploadFile(uploadedFilePath)).toList();

            return uploadFiles;
        } catch (Exception e) {
            log.error("파일 저장 과정에서 에러 발생 {}", e.getMessage());
            e.getStackTrace();
            for (String filePath : uploadedFilePathList) {
                deleteFile(filePath);
            }
            throw new UploadFileException(UploadFileExceptionType.FILE_COULD_NOT_BE_SAVED);
        }
    }


    /**
     * @param id -> File's ID
     * @return-> D:/uploads/UUID.jpg
     */
    @Override
    public String getFullPathById(Long id) throws UploadFileException {
        UploadFile uploadFile = fileRepository.findById(id).orElse(null);

        if (uploadFile == null) {
            throw new UploadFileException(UploadFileExceptionType.FILE_NOT_FOUND);
        }

        return fileDir + uploadFile.getFilePath();
    }



    /**
     * @param filename -> UUID.jpg [UUID + 확장자를 가져와야 함]
     * @return-> D:/uploads/UUID.jpg
     */
    @Override
    public String getFullPath(String filename) {
        return fileDir + filename;
    }



    @Override
    public void deleteFile(String fullPath) {
        System.out.println("파일 삭제 실행");

        File file = new File(fullPath);

        if (file.exists()) {//파일을 삭제
            if (file.delete()) {
                log.info("파일 삭제 성공");
            } else {
                log.error("파일 삭제 실패");
            }
        } else {
            log.error("파일 삭제 실패 -> 에러 발생시켜야 함");
        }

    }

    @Trace
    @Override
    public void deleteFiles(List<UploadFile> uploadFiles) {//UUID 받아서 삭제하기
        uploadFiles.forEach(uploadFile -> deleteFile(uploadFile.getFilePath()));
    }
}