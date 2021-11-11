package com.springweb.web.repository.lesson;

import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.service.lesson.search.LessonSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LessonRepositoryCustom {

    Page<Lesson> search(LessonSearchCond cond, Pageable pageable);
}
