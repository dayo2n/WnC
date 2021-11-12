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

    private boolean fileRemove;

    public boolean isPeriodOk(){
        LocalDateTime start = LocalDate.parse(startPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        if(start.isAfter(end) || start.isEqual(end) || start.isBefore(now) || end.isBefore(now)) {
            return false;
        }
        return true;
    }
    public boolean isMaxStudentCountOk(){
        //그룹이 아니거나, 그룹일 경우 2보다 크면 true
        if(lessonType != LessonType.GROUP || maxStudentCount >= 2){
            return true;
        }
        return false;
    }


    public LocalDateTime getStartPeriodToLocalDateTime() {
        return LocalDate.parse(startPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

    public LocalDateTime getEndPeriodToLocalDateTime() {
        return LocalDate.parse(endPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
    }

}
