package com.springweb.web.service;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.member.StudentDto;
import com.springweb.web.controller.dto.member.TeacherDto;
import com.springweb.web.controller.dto.member.UpdateStudentDto;
import com.springweb.web.controller.dto.member.UpdateTeacherDto;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.MemberRepository;
import com.springweb.web.service.file.FileService;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.context.Theme;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;


    @Trace
    public void save(Member member) throws MemberException {

        //== 중복 아이디 체크 로직 ==//
        checkDuplicateMember(member.getUsername());

        //패스워드 인코딩 해야 함, 중요!!
        member.passwordEncode(passwordEncoder);

        //== 중복 아이디 체크 로직 종료 ==//
        memberRepository.save(member);
    }

    @Trace
    private void checkDuplicateMember(String username) throws MemberException {
        log.info("아이디 중복 검사 시작");
        if (memberRepository.findByUsername(username).orElse(null) != null) {
            log.error("아이디가 중복되었습니다.");
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USERNAME);
        }
    }


    @Trace
    @Transactional(readOnly = true)
    public Object getMyInfo() throws MemberException {//TODO Object로 해도 될까..?
        log.info("내 정보 가져오기 시작");
        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);

        if(member instanceof Student student){
            return new StudentDto(student);
        }
        else if(member instanceof  Teacher teacher){
            return new TeacherDto(teacher);
        }

        Student findMember = (Student)memberRepository.findByUsername(getMyUsername()).orElse(null);
        return new StudentDto(findMember);
    }



    @Trace
    public void update(UpdateStudentDto updateStudentDto) throws MemberException, IOException {
        log.info("내 정보 수정 시작 => 학생");
        Student student = (Student)memberRepository.findByUsername(getMyUsername()).orElse(null);

        changeName(updateStudentDto, student);
        changePassword(updateStudentDto, student);
        changeAge(updateStudentDto, student);
        changeProfileImg(updateStudentDto, student);

    }
    @Trace
    private void changeProfileImg(UpdateStudentDto updateStudentDto, Member member) throws IOException {
        if(updateStudentDto.getProfileImg() != null){
            fileService.deleteFile(member.getProfileImgPath());//원래 저장한 프사 삭제
            String saveFilePath = fileService.saveFile(updateStudentDto.getProfileImg());//새로운 프사 저장
            member.changeProfileImgPath(saveFilePath);
        }
    }//상속받아 구현
    @Trace
    private void changeAge(UpdateStudentDto updateStudentDto,  Member member) {
        if(updateStudentDto.getAge() != 0){
            member.changeAge(updateStudentDto.getAge());
        }
    }
    @Trace
    private void changePassword(UpdateStudentDto updateStudentDto,  Member member) {
        if(StringUtils.hasLength(updateStudentDto.getPassword())){
            member.changePassword(updateStudentDto.getPassword(), passwordEncoder);
        }
    }
    @Trace
    private void changeName(UpdateStudentDto updateStudentDto,  Member member) {
        if(StringUtils.hasLength(updateStudentDto.getName())){
            member.changeName(updateStudentDto.getName());
        }
    }

    @Trace
    private void changeCareer(UpdateTeacherDto updateTeacherDto, Teacher teacher) {
        if(StringUtils.hasLength(updateTeacherDto.getCareer())){
            teacher.changeCareer(updateTeacherDto.getCareer());
        }
    }
    @Trace
    public void update(UpdateTeacherDto updateTeacherDto) throws MemberException, IOException {
        log.info("내 정보 수정 시작 => 선생님");
        Teacher teacher = (Teacher)memberRepository.findByUsername(getMyUsername()).orElse(null);

        changeName(updateTeacherDto, teacher);
        changePassword(updateTeacherDto, teacher);
        changeAge(updateTeacherDto, teacher);
        changeProfileImg(updateTeacherDto, teacher);
        changeCareer(updateTeacherDto, teacher);

    }



    //TODO : 회원 탈퇴시 카카오톡에서도 탈퇴되도록
    @Trace
    public void delete(String password) throws MemberException {
        log.info("회원 탈퇴 시작");
        Member findMember = memberRepository.findByUsername(getMyUsername()).orElse(null);
        checkPasswordEq(password, findMember);

        memberRepository.delete(findMember);
        log.info("회원 탈퇴 성공");
    }


    private void checkPasswordEq(String password, Member findMember) throws MemberException {
        if (!password.equals(findMember.getPassword())) {
            throw new MemberException(MemberExceptionType.PASSWORDS_DOES_NOT_MATCH);
        }
    }







    @Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            log.error("SecurityContextHolder에 있는 username을 가져오던 중 오류 발생");
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }

}
