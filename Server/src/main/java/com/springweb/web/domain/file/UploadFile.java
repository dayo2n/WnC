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
@Table(name = "UPLOAD_FILE")
public class UploadFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UPLOAD_FILE_ID")
    private Long id;


    @Column(unique = true)
    private String filePath;//UUID.jpg(확장자)



    protected UploadFile(String filePath) {
        this.filePath = filePath;
    }

    public static UploadFile createUploadFile(String filePath) {
        return new UploadFile(filePath);
    }

}
