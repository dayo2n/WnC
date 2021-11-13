package com.springweb.web.service.report;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.member.Student;
import com.springweb.web.dto.report.CreateReportDto;
import com.springweb.web.dto.report.ReportDto;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.domain.report.Report;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.exception.report.ReportException;
import com.springweb.web.exception.report.ReportExceptionType;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.repository.report.ReportRepository;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{

    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;


    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            log.error("SecurityContextHolder에 있는 username을 가져오던 중 오류 발생");
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }


    /**
     * 신고하기
     * @param teacherId
     */
    @Override
    public void report(Long teacherId, CreateReportDto createReportDto) throws MemberException, ReportException {
        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(member instanceof Teacher) throw new ReportException(ReportExceptionType.NO_AUTHORITY_REPORT_TEACHER);

        Report report = createReportDto.toEntity();
        report.setWriter((Student) member);
        report.setTarget((Teacher) memberRepository.findById(teacherId).orElse(null));
        reportRepository.save(report);
    }








    //== 어드민만 가능 ==//
    @Override
    @Trace
    public List<ReportDto> getList() {//페이징 귀차나...
        System.out.println(reportRepository.findAllWithWriterAndTargetAndSolverOrderByCreatedDate().size());
        return reportRepository.findAllWithWriterAndTargetAndSolverOrderByCreatedDate()
                .stream().map(ReportDto::new).toList();
    }




    @Override
    public ReportDto readReport(Long reportId) {
        return new ReportDto(reportRepository.findWithStudentAndTeacherAndAdminById(reportId).orElse(null));
    }

    @Override
    public void ignore(Long reportId) {
        Report report = reportRepository.findById(reportId).orElse(null);
        report.solve();
    }


    @Override
    public void addWarning(Long reportId) throws MemberException {

        Report report = reportRepository.findById(reportId).orElse(null);
        Teacher teacher = (Teacher)memberRepository.findById(report.getTarget().getId()).orElse(null);
        if(teacher != null){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }

        teacher.addWarning();

        report.solve();
    }


    @Override
    public void makeBlack(Long reportId ) throws MemberException {
        Report report = reportRepository.findById(reportId).orElse(null);
        Teacher teacher = (Teacher)memberRepository.findById(report.getTarget().getId()).orElse(null);
        if(teacher != null){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }

        teacher.makeBlack();

        report.solve();
    }


    /**
     * 특수하게, 블랙리스트인 사람의 id를 DB에서 직접 보고 풀어준다고 가정
     */
    @Override
    public void makeWhite(Long teacherId) throws MemberException {
        Teacher teacher = (Teacher)memberRepository.findById(teacherId).orElse(null);
        if(teacher != null){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
        teacher.makeWhite();

    }

    @Override
    public List<BlackTeacher> showBlackList() {
        return memberRepository.findAllBlackList().stream().map(teacher -> new BlackTeacher(teacher)).toList();
    }
}
