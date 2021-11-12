package com.springweb.web.domain.lesson;

import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AttributeOverride(name = "id", column = @Column(name = "GROUP_LESSON_ID"))
@DiscriminatorValue("GROUP")
public class GroupLesson extends Lesson{


    private int nowStudentCount;



    private LocalDateTime startPeriod;//과외시작일
    private LocalDateTime endPeriod;//과외종료일


    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TakingLesson> takingLessonList =new ArrayList<>();



    @Builder
    public GroupLesson(String title, String content, Teacher teacher, int maxStudentCount, int nowStudentCount, LocalDateTime startPeriod,LocalDateTime endPeriod) {
        super(title, content, teacher,maxStudentCount);
        this.nowStudentCount = nowStudentCount;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }
    
    
    //== 과외 정보 수정 ==//

    public void changePeriod(LocalDateTime startPeriod, LocalDateTime endPeriod) {
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    public void addTakingLesson(TakingLesson takingLesson){
        this.takingLessonList.add(takingLesson);
        nowStudentCount++;
    }

    public void removeTakingLesson(TakingLesson takingLesson){
        this.takingLessonList.remove(takingLesson);
        nowStudentCount--;
    }


}
