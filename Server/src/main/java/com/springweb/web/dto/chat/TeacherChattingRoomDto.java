package com.springweb.web.dto.chat;

import com.springweb.web.domain.chat.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 선생님인 경우에 채팅방 목록
 */
@Data
public class TeacherChattingRoomDto {
    private Long studentId;//학생 id
    private String studentName; //학생 이름

    private List<MessageContentDto> messageList = new ArrayList<>();//메세지리스트
    private int noReadMessage;//안 읽은 메세지 수

    public void addMessage(Message message){
        messageList.add(new MessageContentDto(message.getContent()));
        if(!message.isTeacherRead()){
            noReadMessage++;
        }
    }
    @Data
    private static  class MessageContentDto{
        private String content;

        public MessageContentDto(String content) {
            this.content = content;
        }
    }
}
