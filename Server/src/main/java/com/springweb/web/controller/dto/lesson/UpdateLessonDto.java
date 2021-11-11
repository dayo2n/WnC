package com.springweb.web.controller.dto.lesson;

import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateLessonDto {

    private String title;//제목
    private String content;//내용
    private List<MultipartFile> uploadFiles = new ArrayList<>();//첨부파일
    private LessonType lessonType; //PERSONAL, GROUP
    private int maxStudentCount;
    private String startPeriod;//모집기간
    private String endPeriod;//모집기간


    public LocalDateTime getStartPeriodToLocalDateTime() {
        return LocalDate.parse(startPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public LocalDateTime getEndPeriodToLocalDateTime() {
        return LocalDate.parse(endPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

}
