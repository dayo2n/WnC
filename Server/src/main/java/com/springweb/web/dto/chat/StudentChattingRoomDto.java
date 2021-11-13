package com.springweb.web.dto.chat;

import com.springweb.web.domain.chat.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 학생인 경우에 채팅방 목록
 */
@Data
public class StudentChattingRoomDto {
    private Long teacherId;//선생님 id
    private String teacherName; //선생님 이름

    private List<String> messageList = new ArrayList<>();//마지막으로 보낸 메세지
    private int noReadMessage;//안 읽은 메세지 수

    public void addMessage(Message message){
        messageList.add(message.getContent());
        if(!message.isTeacherRead()){
            noReadMessage++;
        }
    }
}
