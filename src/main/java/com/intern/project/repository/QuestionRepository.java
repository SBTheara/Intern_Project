package com.intern.project.repository;

import com.intern.project.entity.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository
    extends JpaRepository<Questions, Long>, JpaSpecificationExecutor<Questions> {
}
