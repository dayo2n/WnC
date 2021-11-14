package com.springweb.web.domain.chat;


import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Comparator;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor
@Setter
@Table(name = "MESSAGE")
public class Message implements Comparator<Message> {
    /**
     * 쪽지
     * XX로부터 쪽지가 왔습니다.
     */
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long id;
    private String content;
    private boolean isTeacherRead;
    private boolean isStudentRead;


    private Timestamp writeTime;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;







    @Override
    public int compare(Message o1, Message o2) {
        long l1 = o1.getWriteTime().getTime();
        long l2 = o2.getWriteTime().getTime();

        if(l1 < l2)
            return 1;
        else
            return -1;

    }
}
