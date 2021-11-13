package com.springweb.web.domain.lesson;

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.file.UploadFile;
import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Lesson extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LESSON_ID")
    private Long id;

    private String title;//제목
    private String content;//내용
    private int views;//조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;//작성자

    private boolean isCompleted; //모집완료 여부, true면 모집완료, false면 모집중


    private int maxStudentCount;//모집할 학생 수



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
        @JoinColumn(name = "LESSON_ID")
    private List<UploadFile> uploadFiles = new ArrayList<>();


    

    //== 빌더 사용 ==//
    public Lesson(String title, String content, Teacher teacher, int maxStudentCount) {
        this.title = title;
        this.content = content;
        this.teacher = teacher;
        this.maxStudentCount =maxStudentCount;
    }


    //== 강의 수정 ==//

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeUploadFiles(List<UploadFile> uploadFiles) {
        this.uploadFiles.clear();
        uploadFiles.forEach(uploadFile->this.uploadFiles.add(uploadFile));//바로 갈아끼우면 오류나므로 하나씩 갈아준다
    }

    public void changeMaxStudentCount(int maxStudentCount) {
        this.maxStudentCount = maxStudentCount;
    }


    public void confirmTeacher(Teacher teacher){
        this.teacher=teacher;
        teacher.addLesson(this);
    }


    //== 모집완료 ==//
    public void complete(){
        this.isCompleted=true;
    }

    //== 조회수 & 좋아요 ==//
    public void upView(){
        views++;
    }


}
