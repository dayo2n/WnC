package com.springweb.web.domain.report;

/**
 * 신고 기능
 */

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.member.Admin;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WRITER_ID")
    private Student writer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher target;


    private boolean isSolved; //해결되었는지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_ID")
    private Admin solver;//해결한 관리자


    @Builder
    public Report(String content) {
        this.content = content;
    }


    public void solve(){
        this.isSolved=true;
    }

    public void setWriter(Student writer) {
        this.writer = writer;
    }

    public void setTarget(Teacher target) {
        this.target = target;
    }
}
