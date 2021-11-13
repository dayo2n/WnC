package com.springweb.web.controller.admin;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.member.Member;
import com.springweb.web.dto.admin.LoginAdminDto;
import com.springweb.web.dto.admin.LoginAdminResponse;
import com.springweb.web.dto.admin.SignUpAdminDto;
import com.springweb.web.dto.login.LogInMemberInfoDto;
import com.springweb.web.dto.report.ReportDto;
import com.springweb.web.exception.file.UploadFileException;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.report.ReportException;
import com.springweb.web.jwt.JwtFilter;
import com.springweb.web.jwt.TokenProvider;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.member.MemberService;
import com.springweb.web.service.report.BlackTeacher;
import com.springweb.web.service.report.ReportService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@PreAuthorize("hasRole('ADMIN')")//이거되나?
public class AdminController {

    private final ReportService reportService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;



    /**
     * 신고목록 보기
     */
    @Trace
    @GetMapping("/reports")
    public ResponseEntity getReportList() throws ReportException, MemberException {
        List<ReportDto> list = reportService.getList();

        return new ResponseEntity(new ListReportDto(list) , HttpStatus.OK);
    }

    /**
     * 신고내용 보기
     */
    @Trace
    @GetMapping("/reports/{reportId}")
    public ResponseEntity getReport(@PathVariable("reportId")Long reportId) throws ReportException, MemberException {
        ReportDto reportDto = reportService.readReport(reportId);

        return new ResponseEntity(reportDto , HttpStatus.OK);
    }


    /**
     * 신고 무시하기 -> 읽었으나 아무 처리 X
     */
    @Trace
    @PostMapping("/reports/{reportId}/ignore")
    public ResponseEntity ignore(@PathVariable("reportId")Long reportId) throws ReportException, MemberException {
        reportService.ignore(reportId);
        return new ResponseEntity("신고를 무시했습니다" , HttpStatus.OK);
    }


    /**
     * 경고 주기
     */
    @Trace
    @PostMapping("/reports/{reportId}/warn")
    public ResponseEntity addWarning(@PathVariable("reportId")Long reportId ) throws ReportException, MemberException {
        reportService.addWarning(reportId);

        return new ResponseEntity("경고 요청을 주었습니다." , HttpStatus.OK);
    }

    /**
     * 블랙리스트 만들기
     */
    @Trace
    @PostMapping("/reports/{reportId}/black")
    public ResponseEntity makeBlack(@PathVariable("reportId")Long reportId ) throws ReportException, MemberException {
        reportService.makeBlack(reportId);

        return new ResponseEntity("블랙리스트로 만들었습니다." , HttpStatus.OK);
    }

    /**
     * 블랙리스트에서 원상복구히키기
     */
    @Trace
    @PostMapping("/reports/white/{teacherId}")
    public ResponseEntity makeWhite(@PathVariable("teacherId")Long teacherId) throws ReportException, MemberException {
        reportService.makeWhite(teacherId);

        return new ResponseEntity(teacherId+"번의 선생님을 블랙리스트에서 해제했습니다.", HttpStatus.OK);
    }


    /**
     * 블랙리스트 목록 조회
     */
    @Trace
    @GetMapping("/reports/blacklist")
    public ResponseEntity showBlackList() throws ReportException, MemberException {
        List<BlackTeacher> blackTeachers = reportService.showBlackList();

        return new ResponseEntity(blackTeachers, HttpStatus.OK);
    }




    @Trace
    @PostMapping("/admin/signUp")
    @PreAuthorize("permitAll()")//이거되나?
    public ResponseEntity signUp(@ModelAttribute SignUpAdminDto signUpAdminDto) throws UploadFileException, IOException, MemberException {
        memberService.save(signUpAdminDto.toEntity(),null);
        return new ResponseEntity(HttpStatus.OK);
    }


    @Trace
    @PostMapping("/admin/login")
    @PreAuthorize("permitAll()")//이거되나?
    public ResponseEntity logIn(@ModelAttribute LoginAdminDto loginAdminDto) throws UploadFileException, IOException, MemberException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginAdminDto.getUsername(), loginAdminDto.getPassword());

        String jwt = authenticationAndGeneratingToken(authenticationToken);
        HttpHeaders httpHeaders = setTokenInHeader(jwt);

        Member member = memberRepository.findByUsername(loginAdminDto.getUsername()).orElse(null);
        LoginAdminResponse loginAdminResponse =new LoginAdminResponse(jwt,member.getId(), reportService.getList());

        return new ResponseEntity(loginAdminResponse, httpHeaders, HttpStatus.OK);
    }







    private HttpHeaders setTokenInHeader(String jwt) {
        //== 토큰 전송 로직 ==//
        //JWT를 헤더와 body에 모두 넣어준다
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);//Authorization Bearer [토큰정보]
        log.info("전송한 토큰 정보{}", jwt);
        return httpHeaders;
    }



    private String authenticationAndGeneratingToken(UsernamePasswordAuthenticationToken authenticationToken) {
        //== 권한 부여 로직 => 이후 loadUserByUsername 실행 ==//
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);//CustomDetailService의 loadByUsername이 실행
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //== 토큰 생성 로직 ==//
        return tokenProvider.createToken(authentication);
    }




    @Data
    private static class ListReportDto{
        private int totalReportCount;

        private List<ReportDto> reportDtos;

        public ListReportDto(List<ReportDto> reportDtos) {
            this.totalReportCount = reportDtos.size();
            this.reportDtos = reportDtos;
        }
    }

}
