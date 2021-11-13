package com.springweb.web.repository.member;

import com.springweb.web.domain.member.Teacher;
import com.springweb.web.service.member.search.TeacherSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    Page<Teacher> search(TeacherSearchCond cond, Pageable pageable);
}
