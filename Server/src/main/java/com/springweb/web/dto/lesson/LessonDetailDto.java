package com.springweb.web.dto.lesson;

import com.springweb.web.dto.file.UploadFileDto;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonDetailDto {

    private Long id;//과외 id
    private String title;//제목
    private String content;//내용
    private int views;//조회수
    private LocalDateTime createdDate; //작성일
    private boolean isCompleted; //모집완료 여부, true면 모집완료, false면 모집중

    private LessonType lessonType; //과외 타입 , PERSONAL, GROUP
    private int maxStudentCount;//모집인원
    private int nowStudentCount;//현재 신청한 학생 수
    private LocalDateTime startPeriod;//모집기간
    private LocalDateTime endPeriod;//모집기간

    private SimpleTeacherDto teacher;//작성자
    private List<UploadFileDto> uploadFiles = new ArrayList<>();

    public LessonDetailDto(Lesson findLesson) {
        this.id                 =findLesson.getId();
        this.title              =findLesson.getTitle();
        this.content            =findLesson.getContent();
        this.views              =findLesson.getViews();
        this.createdDate        =findLesson.getCreatedDate();
        this.isCompleted        =findLesson.isCompleted();


        this.teacher            =new SimpleTeacherDto(findLesson.getTeacher());//페치조인으로 해결
        this.uploadFiles = findLesson.getUploadFiles().stream().map(uploadFile -> new UploadFileDto(uploadFile)).toList();//배치사이즈로 해결

        //개인과외일 경우
        if (findLesson instanceof PersonalLesson){
            this.lessonType=LessonType.PERSONAL;
            return;
        }

        //그룹과외일 경우
        if(findLesson instanceof GroupLesson group){
            this.lessonType=LessonType.GROUP;
            this.maxStudentCount=group.getMaxStudentCount();
            this.nowStudentCount=group.getNowStudentCount();
            this.startPeriod=group.getStartPeriod();
            this.endPeriod=group.getEndPeriod();
            return;
        }

    }

    @Data
    private static class SimpleTeacherDto {

        private Long teacherId;
        private String name;

        public SimpleTeacherDto(Teacher teacher) {
            this.name = teacher.getName();
            this.teacherId = teacher.getId();

        }
    }

}
