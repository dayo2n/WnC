package com.springweb.web.controller.chat;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.dto.chat.MessageDto;
import com.springweb.web.dto.chat.StudentChattingRoomDto;
import com.springweb.web.dto.chat.TeacherChattingRoomDto;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.chat.MessageRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.chat.MessageService;
import com.springweb.web.util.SecurityUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;


    /**
     * 메세지 입력
     *      * 내가 선생일 경우 => 메세지.학생read = false,
     *      * 내가 학생일 경우 => 메세지.선생read = false
     */
    @PostMapping("/chat/insert")
    public String chat_insert(@RequestBody SendMessageDto sendMessageDto) throws Exception {
        messageService.save(sendMessageDto.getReceiverId(), sendMessageDto.getContent());// 채팅방에도 저장
        return "/chat/list/"+sendMessageDto.getReceiverId();
    }

    @Data
    private static class SendMessageDto{
        private String content;
        private Long receiverId;
    }


    /**
     *선생님과 체팅하기 클릭 시 이곳으로 이동
     *
     * 채팅방 입장  & 채팅 입력시 반영
     *
     * 내 id는 당연히 알고. 상대방 id도 아니까,  /chat/insert?내id=1&상대방id=2&메세지=123   해서 ㄱㄱ
     *
     *
     * 내가 선생일 경우 => 메세지.선생read = true,
     * 내가 학생일 경우 => 메세지.학생read = true
     *
     *
     */
    @RequestMapping("/chat/list/{receiverId}")
    @ResponseBody
    public ResponseEntity showChatList(@PathVariable Long receiverId) throws Exception {
        List<MessageDto> chatList = messageService.enterChatRoom(receiverId);
        return new ResponseEntity(chatList, HttpStatus.OK);

    }




    /**
     * 내 채팅방 목록 조회,
     * 선생의 경우, 선생 아이디가 내 id인 메세지를 모두 가져와서, 각각의 학생 id로 매핑=> 개수를 세서 그룹화,
     *
     * 학생의 경우, 힉생 아아디가 내 id인 메세지를 모두 가져와서 가각의 선생 id로 매핑
     */
    @GetMapping("/chat/list")
    @ResponseBody
    public ResponseEntity getMyChattingList() throws MemberException {

        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(member instanceof Teacher){
            List<TeacherChattingRoomDto> myChattingListCaseTeacher = messageService.getMyChattingListCaseTeacher((Teacher) member);
            return new ResponseEntity(myChattingListCaseTeacher,HttpStatus.OK);
        }else {
            List<StudentChattingRoomDto> myChattingListCaseStudent = messageService.getMyChattingListCaseStudent((Student) member);
            return new ResponseEntity(myChattingListCaseStudent,HttpStatus.OK);
        }
    }
















    @Trace
    @ResponseBody
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }
}
