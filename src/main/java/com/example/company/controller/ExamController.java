package com.example.company.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.company.dto.ExamDTO;
import com.example.company.model.Exam;
import com.example.company.model.Question;
import com.example.company.repository.ExamRepository;
import com.example.company.serveice.ExamService;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class ExamController extends BaseController{

	@Autowired
	private ExamRepository examRepository;
	@Autowired
	private ExamService examService;
	/**
	 * 打开部门试卷列表页
	 */
	@RequestMapping("/examController_openList")
	public String openList() {
		return "exam/examList";
	}

	/**
	 * 获取试卷分页列表
	 */
	@RequestMapping("/examController_examList")
	public void examList(@RequestParam(value = "examname", required = false) String examname,@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response) {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Exam> dicList = examRepository.findAllByExamnameLike(
				examname == null ? "%%" : "%" + examname + "%", pageable);
		System.out.println("总条数：" + dicList.getTotalElements());
		System.out.println("总页数：" + dicList.getTotalPages());
		if (dicList != null && dicList.getSize() > 0) {
			PageUtils<Exam> pages = new PageUtils<Exam>(Integer.valueOf((int) dicList.getTotalElements()),
					page, rows, dicList.getContent());
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
	 * 打开新增试卷页
	 */
	@RequestMapping("/examController_openAddPage")
	public String openAddPage() {
		return "exam/examAdd";
	}

	/**
	 * 保存试卷信息
	 */
	@RequestMapping("/examController_saveExam")
	public void saveExam(@ModelAttribute ExamDTO dto, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Exam exam = new Exam();
		try {
			BeanUtils.copyProperties(exam, dto);
			exam.setHasquestion(false);
			exam = examRepository.save(exam);
			MsgIn<Exam> in = new MsgIn<Exam>("0000","更新成功");
			MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
			writeResponse(response, out.getJsonstring());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改试卷信息
	 */
	@RequestMapping("/examController_updateExam")
	public void updateExam(@ModelAttribute ExamDTO dto,HttpServletRequest request, HttpSession session, HttpServletResponse response) {

		Exam exam = examRepository.findOne(dto.getId());
		if(exam!=null){
			exam.setExamname(dto.getExamname());
			exam.setTimelength(dto.getTimelength());
			exam.setFullmarks(dto.getFullmarks());
			exam.setPassingmark(dto.getPassingmark());
			exam.setQuestionnum(dto.getQuestionnum());
			exam = examRepository.save(exam);
		}
		MsgIn<Exam> in = new MsgIn<Exam>("0000","更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 删除试卷
	 */
	@RequestMapping("/examController_deleteExams")
	public void deleteExams(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for(String id :idsArray){
			examRepository.delete(Long.valueOf(id));
		}
		MsgIn<Exam> in = new MsgIn<Exam>("0000","删除成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * 打开修改页面
	 */
	@RequestMapping("/examController_openUpdatePage")
	public String openUpdatePage(@RequestParam("id") Long id,Model model) {
		Exam exam = examRepository.findOne(id);
		MsgIn<Exam> in = new MsgIn<Exam>(exam);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("exam", out.getTobj());
		return "exam/examUpdate";
	}
	/**
	 * GETCOMBOX
	 */
	@RequestMapping("/examController_getAll")
	public void getAll(HttpServletResponse response) {
		List<Exam> examList = examRepository.findAll();
		MsgIn<List<Exam>> in = new MsgIn<List<Exam>>(examList);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
		writeResponse(response, out.getTjson());
		return;
	}
	/**
	 * 打开新增试卷页
	 */
	@RequestMapping("/examController_startTheExam")
	public String startTheExam(@RequestParam("id") Long id,Model model) {
		List<Question> list = examService.getquestions(id);
		MsgIn<List<Question>> in = new MsgIn<List<Question>>(list);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
		model.addAttribute("question", out.getTobj());
		return "exam/startTheExam";
	}
	
	/**
	 * 提交 计算
	 */
	@RequestMapping("/examController_startExam")
	public void submitexam(HttpServletResponse response) {
		List<Exam> examList = examRepository.findAll();
		MsgIn<List<Exam>> in = new MsgIn<List<Exam>>(examList);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
		writeResponse(response, out.getTjson());
		return;
	}
	
}
