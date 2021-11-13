package com.springweb.web.service.chat;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.chat.MessageController;
import com.springweb.web.domain.chat.Message;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.domain.report.Report;
import com.springweb.web.dto.chat.MessageDto;
import com.springweb.web.dto.chat.StudentChattingRoomDto;
import com.springweb.web.dto.chat.TeacherChattingRoomDto;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.exception.report.ReportException;
import com.springweb.web.exception.report.ReportExceptionType;
import com.springweb.web.repository.chat.MessageRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    private final MemberRepository memberRepository;


    /**
     * 메세지 입력
     *      * 내가 선생일 경우 => 메세지.학생read = false,
     *      * 내가 학생일 경우 => 메세지.선생read = false
     */
    public void save(Long receiverId, String content) throws MemberException, ReportException {

        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);

        Member receiver = memberRepository.findById(receiverId).orElse(null);

        Message message = new Message();
        message.setWriteTime(new Timestamp(System.currentTimeMillis()));
        message.setContent(content);


        if(member instanceof Student student){//내가 학생일때 상대가 학생이면 오류
            if(receiver instanceof Student){
                throw new MemberException(MemberExceptionType.BAD_REQUEST);
            }

            message.setStudent(student);
            message.setTeacher((Teacher) receiver);
            message.setStudentRead(true);//나는 읽었고 넌 안읽음
            message.setTeacherRead(false);
        }else{
            if(receiver instanceof Teacher){
                throw new MemberException(MemberExceptionType.BAD_REQUEST);
            }

            message.setStudent((Student) receiver);
            message.setTeacher((Teacher) member);
            message.setStudentRead(false);
            message.setTeacherRead(true);
        }
        messageRepository.save(message);
    }



    /**
     * 내 채팅방 목록 조회,
     * 선생의 경우, 선생 아이디가 내 id인 메세지를 모두 가져와서, 각각의 학생 id로 매핑=> 개수를 세서 그룹화,
     *
     * 학생의 경우, 힉생 아아디가 내 id인 메세지를 모두 가져와서 가각의 선생 id로 매핑
     */
    public List<TeacherChattingRoomDto> getMyChattingListCaseTeacher(Teacher teacher) throws MemberException {


            List<Message> messageList = messageRepository.findAllWithStudentWithTeacherByTeacher(teacher);
            List<TeacherChattingRoomDto> messages = new ArrayList<>();
            Set<Long> studentIdList = messageList.stream().map(message -> message.getStudent().getId()).collect(Collectors.toSet());//학생의 id만 뽑아냄

            for (Long id : studentIdList) {
                TeacherChattingRoomDto teacherChattingRoomDto = new TeacherChattingRoomDto();
                teacherChattingRoomDto.setStudentId(id);
                teacherChattingRoomDto.setStudentName(memberRepository.findById(id).orElse(null).getName());
                for (Message message : messageList) {
                    if(message.getStudent().getId().equals(id)){
                        teacherChattingRoomDto.addMessage(message);
                    }
                }
                messages.add(teacherChattingRoomDto);
            }
            return messages;


    }
    public List<StudentChattingRoomDto> getMyChattingListCaseStudent(Student student) throws MemberException {

        //학생인 경우
            List<Message> messageList = messageRepository.findAllWithStudentWithTeacherByStudent(student);
            List<StudentChattingRoomDto> messages = new ArrayList<>();
            Set<Long> teacherIdList = messageList.stream().map(message -> message.getTeacher().getId()).collect(Collectors.toSet());//선생님의 id만 뽑아냄


            for (Long id : teacherIdList) {
                StudentChattingRoomDto studentChattingRoomDto = new StudentChattingRoomDto();
                studentChattingRoomDto.setTeacherId(id);
                studentChattingRoomDto.setTeacherName(memberRepository.findById(id).orElse(null).getName());
                for (Message message : messageList) {
                    if(message.getTeacher().getId().equals(id)){
                        studentChattingRoomDto.addMessage(message);
                    }
                }
                messages.add(studentChattingRoomDto);
            }
            return messages;

    }







    public List<MessageDto> enterChatRoom(Long receiverId) throws MemberException {
        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);
        List<Message> studentChatting = new ArrayList<>();
        List<Message> teacherChatting = new ArrayList<>();



        if(member instanceof Student){
            List<Message> myChatting = messageRepository.findAllByStudentIdAndTeacherId(member.getId(), receiverId);
            studentChatting = myChatting;


            Message ccc = new Message();
            Collections.sort(studentChatting, ccc); // 역순으로 정렬하기*/

            studentChatting.forEach(message -> message.setStudentRead(true));//읽음으로 처리
            return studentChatting.stream().map(message -> new MessageDto(message)).toList();
        }


        else {
            List<Message> myChatting = messageRepository.findAllByStudentIdAndTeacherId( receiverId,member.getId());
            teacherChatting = myChatting;


            Message ccc = new Message();
            Collections.sort(teacherChatting, ccc); // 역순으로 정렬하기*/

            teacherChatting.forEach(message -> message.setTeacherRead(true));//읽음으로 처리
            return teacherChatting.stream().map(message -> new MessageDto(message)).toList();
        }
    }


    /**
     * 로그인 시 안 읽은 채팅이 있는지 확인여ㅑ부
     */
    public int getMyNoReadChatCount(Student student) throws MemberException {
        int result = 0;
        //학생인 경우
        List<Message> messageList = messageRepository.findAllWithStudentWithTeacherByStudent(student);
        for (Message message : messageList) {
            if(!message.isStudentRead()){
                result++;
            }
        }
        return result;
    }
    @Trace
    public int getMyNoReadChatCount(Teacher teacher) throws MemberException {
        int result = 0;
        //선생
        List<Message> messageList = messageRepository.findAllWithStudentWithTeacherByTeacher(teacher);
        for (Message message : messageList) {
            if(!message.isTeacherRead()){
                result++;
            }
        }
        return result;
    }















    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }
}
