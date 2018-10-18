package com.example.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {

	Page<Exam> findAllByExamnameLike(String examname,Pageable pageable);

}
