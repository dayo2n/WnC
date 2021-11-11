package com.springweb.web.service.lesson.search;

import com.springweb.web.service.lesson.LessonType;
import lombok.Data;

@Data
public class LessonSearchCond {

    /**
     * 검색조건 : 제목, 선생님 이름, 모집 인원, 내용, 개인과외인지 그룹과외인지 전체인지
     */

    private String title;
    private String teacherName;
    private String content;

    private LessonType lessonType;// ALL , PERSONAL, GROUP
    private int maxStudentCount;

}


