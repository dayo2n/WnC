package com.springweb.web.repository.chat;

import com.springweb.web.domain.chat.Message;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByStudentIdAndTeacherId(Long writerId, Long receiverId);



    @EntityGraph(attributePaths = {"student", "teacher"})
    List<Message> findAllWithStudentWithTeacherByTeacher(Teacher teacher);

    @EntityGraph(attributePaths = {"student", "teacher"})
    List<Message> findAllWithStudentWithTeacherByStudent(Student student);
}
