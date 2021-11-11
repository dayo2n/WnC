package com.springweb.web.controller.dto.lesson;

import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    //TODO  :lessonType이 GROUP일 경우 최소 2 이상
    private int maxStudentCount;

    private LocalDateTime period;//모집기간



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
                    .period(period)
                    .build();
        }


        log.error("여기까지 오면 에러가 발생해야 합니다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return null;  // => 둘 다 아니면 오류 발생

    }
}
