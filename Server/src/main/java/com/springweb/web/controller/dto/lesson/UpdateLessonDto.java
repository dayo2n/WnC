package com.springweb.web.controller.dto.lesson;

import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateLessonDto {

    private String title;//제목
    private String content;//내용

    private List<MultipartFile> uploadFiles = new ArrayList<>();//첨부파일

    private LessonType lessonType; //PERSONAL, GROUP

    private int maxStudentCount;

    private LocalDateTime period;//모집기간
}
