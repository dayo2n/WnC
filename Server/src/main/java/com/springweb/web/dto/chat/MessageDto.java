package com.springweb.web.dto.chat;

import com.springweb.web.domain.chat.Message;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageDto {

    private Long id;
    private String studentName;
    private Long studentId;

    private String teacherName;
    private Long teacherId;

    private Timestamp writeTime;
    private String content;

    private boolean isStudentRead;
    private boolean isTeacherRead;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.studentName = message.getStudent().getName();
        this.studentId = message.getStudent().getId();
        this.teacherName = message.getTeacher().getName();
        this.teacherId = message.getTeacher().getId();
        this.writeTime = message.getWriteTime();
        this.content = message.getContent();

        this.isTeacherRead = message.isTeacherRead();
        this.isStudentRead = message.isStudentRead();
    }
}