package com.example.company.serveice;

import java.util.List;

import com.example.company.model.Question;

public interface ExamService {

	List<Question> getquestions(Long id);
}
