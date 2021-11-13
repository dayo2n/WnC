package com.springweb.web.service.member.search;

import com.springweb.web.service.lesson.LessonType;
import lombok.Data;

@Data
public class TeacherSearchCond {

    /**
     * 검색조건 : 선생님 이름, (평점이 X점 이상인 선생님)평점 순,
     */

    private String teacherName;

    //private int starPoint;
    private boolean isDESC;
}
