package com.springweb.web.domain.lesson.recommend;

import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Recommend {

    @Id
    @GeneratedValue
    @Column(name = "RECOMMEND_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;//좋아요를 누를 사람


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;//좋아요를 누른 강의


}
