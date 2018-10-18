package com.example.company.serveice.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.company.model.Exam;
import com.example.company.model.Question;
import com.example.company.repository.ExamRepository;
import com.example.company.repository.QuestionRepository;
import com.example.company.serveice.ExamService;

@Service
@Transactional
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Override
	public List<Question> getquestions(Long id) {
		
		List<Question> retList = new ArrayList<>();
		Exam exam = examRepository.findOne(id);
		if(exam!=null){
			if(exam.getHasquestion()==false)return null;
			long count = questionRepository.countByExamid(id);
			List<BigInteger> queIds = questionRepository.selectIdsByExamid();
			int questionnum = exam.getQuestionnum();
			List<Long> quesList = new ArrayList<>();
			while(quesList.size()<questionnum){
				Random r = new Random();
				int q =r.nextInt((int) count);
				if(!quesList.contains(queIds.get(q).longValue())){
					quesList.add((long) queIds.get(q).intValue());
				}
//				Question rQuestion = questionRepository.findOne(q);
//				if(rQuestion!=null){
//					retList.add(rQuestion);
//				}
			}
			retList =  questionRepository.findAll(quesList);
		}
		return retList;
	}
}
