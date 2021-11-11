package com.springweb.web.domain.lesson;

import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "GROUP_LESSON_ID"))
@DiscriminatorValue("GROUP")
public class GroupLesson extends Lesson{


    private int nowStudentCount;

    private LocalDateTime period;//모집기간


    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)//TODO :이거 되는지 확인, 상속 좀 어지럽네
    private List<TakingLesson> lessonList =new ArrayList<>();



    @Builder
    public GroupLesson(String title, String content, Teacher teacher, int maxStudentCount, int nowStudentCount, LocalDateTime period) {
        super(title, content, teacher,maxStudentCount);
        this.nowStudentCount = nowStudentCount;
        this.period = period;
    }
    
    
    //== 과외 정보 수정 ==//

    public void changePeriod(LocalDateTime period) {
        this.period = period;
    }
}
