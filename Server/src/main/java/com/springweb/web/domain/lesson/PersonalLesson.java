package com.springweb.web.domain.lesson;

import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AttributeOverride(name = "id", column = @Column(name = "PRIVATE_LESSON_ID"))
@DiscriminatorValue("PERSONAL")
public class PersonalLesson extends Lesson{



    @Builder
    public PersonalLesson(String title, String content, Teacher teacher, int maxStudentCount) {
        super(title, content, teacher, maxStudentCount);
    }


}
