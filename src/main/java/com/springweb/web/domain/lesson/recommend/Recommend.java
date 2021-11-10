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


    //TODO : Long으로 id만 때려넣을 것인가 아니면 post등을 다 넣을것인가..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;//좋아요를 누른 강의





}
