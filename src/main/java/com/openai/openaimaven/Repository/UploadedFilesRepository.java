package com.openai.openaimaven.Repository;

import com.openai.openaimaven.Entity.UploadedFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFilesRepository extends JpaRepository<UploadedFiles,Long> {
}
