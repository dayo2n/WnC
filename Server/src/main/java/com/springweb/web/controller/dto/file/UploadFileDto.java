package com.springweb.web.controller.dto.file;

import com.springweb.web.domain.file.UploadFile;
import lombok.Data;


@Data
public class UploadFileDto {

    private String filePath;//전체 경로

    public UploadFileDto(UploadFile uploadFile) {
        this.filePath = uploadFile.getFilePath();
    }
}
