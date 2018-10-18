package com.example.company.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.company.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	Page<Question> findAllByQuesubjectLike(String quesubject, Pageable pageable);
	
	List<Question> findAllByExamid(Long examid);
	
	Long countByExamid(Long examid);
	
	@Query(value="select id from question", nativeQuery = true)
	List<BigInteger> selectIdsByExamid();
}
