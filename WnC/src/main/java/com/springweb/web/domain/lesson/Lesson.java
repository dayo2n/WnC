package com.springweb.web.domain.lesson;

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.domain.lesson.recommend.Recommend;
import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Lesson extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "LESSON_ID")
    private Long id;

    private String title;//제목
    private String content;//내용
    private int views;//조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;//작성자

    private int recommendCount;//좋아요 개수



    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadFile> uploadFiles = new ArrayList<>();

    //== 중복 추천 방지 ==//
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommend> recommends = new ArrayList<>();

}
