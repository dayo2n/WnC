package com.springweb.web.service.file;

import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.domain.member.Member;
import com.springweb.web.exception.file.UploadFileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    String saveFile(MultipartFile file) throws UploadFileException, IOException, IOException;//DB에 파일의 이름(경로 저장)


    /**
     * 파일들을 저장하다 오류가 발생하면
     * 저장한 파일들을 모두 삭제
     */
    List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws UploadFileException, IOException;//컴퓨터에 파일들 저장



    //파일의 저장 경로 가져오기
    String getFullPathById(Long id) throws UploadFileException;//return은 완전한 경로

    String getFullPath(String filename); //확장자를 포함한 모든 경로


    /**
     * 디비에서는 삭제 안 함
     * 여기서 삭제해버리면 CaseCade랑 뭐 이것저것때문에 안돼
     */
    void deleteFile(String fullPath);

    /**
     * If files save fails
     * Delete all files saved so far
     * <p>
     * 만약 파일을 저장하다 실패했다면,
     * 지금까지 저장한 파일들을 모두 삭제
     */
    void deleteFiles(List<UploadFile> uploadFiles);


}
