package com.springweb.web.domain.member;


import com.springweb.web.domain.lesson.TakingLesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AttributeOverride(name = "id", column = @Column(name = "STUDENT_ID"))
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("S")
public class Student extends Member {

    /**
     * 신청한 강의 목록
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TakingLesson> takingLectureList = new ArrayList<>();




    @Builder
    public Student(Long id, String username, String password, String name, int age, String profileImgPath, Role role, Long kakaoId, boolean isKakaoMember, boolean activated) {
        super(id, username, password, name, age, profileImgPath, role, kakaoId, isKakaoMember, activated);
    }
}
