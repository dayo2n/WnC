package com.springweb.web.controller.dto.lesson;

import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class CreateLessonDto {

    @NotNull
    private String title;//제목

    @NotNull
    private String content;//내용

    private List<MultipartFile> uploadFiles = new ArrayList<>();//첨부파일

    @NotNull
    private LessonType lessonType; //PERSONAL, GROUP

    private int maxStudentCount;

    private String startPeriod;//시작기간
    private String endPeriod;//과외 끝기간


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


    public Lesson toEntity(){
        if(lessonType == LessonType.PERSONAL){
            return PersonalLesson.builder()
                    .title(title)
                    .content(content)
                    .maxStudentCount(1)
                    .build();
        }

        if(lessonType == LessonType.GROUP){
            return GroupLesson.builder()
                    .title(title)
                    .content(content)
                    .maxStudentCount(maxStudentCount)
                    .nowStudentCount(0)
                    .startPeriod(LocalDate.parse(startPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                    .endPeriod(LocalDate.parse(endPeriod, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay())
                    .build();
        }


        return null;  // => 둘 다 아니면 오류 발생

    }
}
