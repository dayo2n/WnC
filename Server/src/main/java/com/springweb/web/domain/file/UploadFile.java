package com.springweb.web.domain.file;

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.lesson.Lesson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "UPLOAD_FILE_ID")
    private Long id;


    @Column(unique = true)
    private String filePath;//UUID.jpg(확장자)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;


    protected UploadFile(String filePath) {
        this.filePath = filePath;
    }

    public static UploadFile createUploadFile(String filePath) {
        return new UploadFile(filePath);
    }

}
