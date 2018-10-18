package com.example.company.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.company.dto.QuestionDTO;
import com.example.company.model.Exam;
import com.example.company.model.FileStorage;
import com.example.company.model.Meeting;
import com.example.company.model.Question;
import com.example.company.repository.ExamRepository;
import com.example.company.repository.QuestionRepository;
import com.example.company.serveice.FileUploadService;
import com.example.company.serveice.impl.ImportExcelImpl;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class QuestionController extends BaseController {

	@Value("${local.storage.root}")
	private String rootPath;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private ImportExcelImpl importExcelImpl;

	/**
	 * 打开部门试题列表页
	 */
	@RequestMapping("/questionController_openList")
	public String openList() {
		return "question/questionList";
	}

	@RequestMapping("/questionController_openUpload")
	public String openUpload() {
		return "question/questionUpload";
	}

	/**
	 * 获取试题分页列表
	 */
	@RequestMapping("/questionController_questionList")
	public void questionList(@RequestParam(value = "quesubject", required = false) String quesubject,
			@RequestParam("page") Integer page, @RequestParam("rows") Integer rows, HttpServletResponse response) {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Question> dicList = questionRepository
				.findAllByQuesubjectLike(quesubject == null ? "%%" : "%" + quesubject + "%", pageable);
		System.out.println("总条数：" + dicList.getTotalElements());
		System.out.println("总页数：" + dicList.getTotalPages());
		if (dicList != null && dicList.getSize() > 0) {
			PageUtils<Question> pages = new PageUtils<Question>(Integer.valueOf((int) dicList.getTotalElements()), page,
					rows, dicList.getContent());
			// 封装返回信息
			Map<String, Object> result = pages.getResult(dicList.getContent());
			MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(result);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);
			String retString = out.getTjson();
			writeResponse(response, retString);
		}
		return;
	}

	/**
	 * 打开新增试题页
	 */
	@RequestMapping("/questionController_openAddPage")
	public String openAddPage() {
		return "question/questionAdd";
	}

	/**
	 * 保存试题信息
	 */
	@RequestMapping("/questionController_saveQuestion")
	public void saveQuestion(@ModelAttribute QuestionDTO dto, HttpServletRequest request, HttpSession session,
			HttpServletResponse response) {
		Question question = new Question();
		try {
			BeanUtils.copyProperties(question, dto);
			Exam exam = examRepository.findOne(dto.getExamid());
			question.setExamname(exam.getExamname());
			question = questionRepository.save(question);
			exam.setHasquestion(true);
			examRepository.save(exam);
			MsgIn<Question> in = new MsgIn<Question>("0000", "更新成功");
			MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
			writeResponse(response, out.getJsonstring());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改试题信息
	 */
	@RequestMapping("/questionController_updateQuestion")
	public void updateQuestion(@ModelAttribute QuestionDTO dto,HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Question question = questionRepository.findOne(dto.getId());
		if (question != null) {
			question.setQuesubject(dto.getQuesubject());
			question.setQueone(dto.getQueone());
			question.setQuetwo(dto.getQuetwo());
			question.setQuethree(dto.getQuethree());
			question.setQuefour(dto.getQuefour());
			question.setRightque(dto.getRightque());
			question = questionRepository.save(question);
		}
		MsgIn<Question> in = new MsgIn<Question>("0000", "更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 删除试题
	 */
	@RequestMapping("/questionController_deleteQuestions")
	public void deleteQuestions(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			questionRepository.delete(Long.valueOf(id));
		}
		MsgIn<Question> in = new MsgIn<Question>("0000", "删除成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * 打开修改页面
	 */
	@RequestMapping("/questionController_openUpdatePage")
	public String openUpdatePage(@RequestParam("id") Long id, Model model) {
		Question question = questionRepository.findOne(id);
		MsgIn<Question> in = new MsgIn<Question>(question);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("question", out.getTobj());
		return "question/questionUpdate";
	}

	@RequestMapping("/questionController_uploadTempl")
	public void updateMeeting(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletResponse response) {
		FileStorage fileStorage = this.fileUploadService.upload(file);
		if (fileStorage != null) {
			String basePath = rootPath +"/"+fileStorage.getNo()+fileStorage.getSuffixName();
			try {
				importExcelImpl.importexcel(basePath);
				
				MsgIn<Meeting> in = new MsgIn<Meeting>("0000", "更新成功");
				MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
				writeResponse(response, out.getJsonstring());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
