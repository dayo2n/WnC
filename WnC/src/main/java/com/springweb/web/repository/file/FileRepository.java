package com.springweb.web.repository.file;

import com.springweb.web.domain.file.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadFile, Long> {
}
