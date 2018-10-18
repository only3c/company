package com.example.company.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.company.dto.LoginUserDTO;
import com.example.company.dto.NoteDTO;
import com.example.company.model.Note;
import com.example.company.repository.InstInfoRepository;
import com.example.company.repository.MeetingRepository;
import com.example.company.repository.NoteRepository;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.NoteService;
import com.example.company.util.Constant;
import com.example.company.util.DateUtil;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class NoteController extends BaseController{

	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private NoteService noteService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private MeetingRepository meetingRepository;

	/**
	 * 打开部门笔记列表页
	 */
	@RequestMapping("/noteController_openList")
	public String openList() {
		return "note/noteList";
	}

	/**
	 * 获取笔记分页列表
	 */
	@RequestMapping("/noteController_noteList")
	public void noteList(@RequestParam(value = "meetingname", required = false) String meetingname,@RequestParam(value = "notes", required = false) String notes,
			@RequestParam("page") Integer page,@RequestParam("rows") Integer rows, HttpServletResponse response, HttpSession session) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Note> dicList = noteService.getNotePageByUsercode(user.getUsercode(),meetingname,notes, pageable);
		System.out.println("总条数：" + dicList.getTotalElements());
		System.out.println("总页数：" + dicList.getTotalPages());
		if (dicList != null && dicList.getSize() > 0) {
			PageUtils<Note> pages = new PageUtils<Note>(Integer.valueOf((int) dicList.getTotalElements()), page,
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
	 * 打开新增笔记页
	 */
	@RequestMapping("/noteController_openAddPage")
	public String openAddPage() {
		return "note/noteAdd";
	}

	/**
	 * 保存笔记信息
	 */
	@RequestMapping("/noteController_saveNote")
	public void saveNote(@ModelAttribute NoteDTO dto, HttpSession session, HttpServletResponse response) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		Note note = new Note();
		note.setNotename(dto.getNotename());
		note.setMeetingname(meetingRepository.findOne(dto.getMeetingid()).getSubject());
		note.setInstcode(user.getInstcode());
		note.setInstname(instInfoRepository.findOne(user.getInstcode()).getInstname());
		note.setUsercode(user.getUsercode());
		note.setUsername(userRepository.findOne(user.getUsercode()).getUsercode());
		note.setNotes("");
		note.setUpdatetime(DateUtil.getDateTime());
		note = noteRepository.save(note);
		MsgIn<Note> in = new MsgIn<Note>("0000", "保存成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 修改笔记信息
	 */
	@RequestMapping("/noteController_updateNote")
	public void updateNote(@ModelAttribute NoteDTO dto, HttpServletRequest request, HttpSession session,
			HttpServletResponse response,@RequestParam("id") long id,@RequestParam("notes") String notes) {
//		Long id = Long.valueOf(request.getParameter("id"));
		Note note = noteRepository.findOne(id);
		if (note != null) {
			note.setNotes(dto.getNotes());
			note = noteRepository.save(note);
		}
		MsgIn<Note> in = new MsgIn<Note>("0000", "更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 删除笔记
	 */
	@RequestMapping("/noteController_deleteNotes")
	public void deleteNotes(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			noteRepository.delete(Long.valueOf(id));
		}
		MsgIn<Note> in = new MsgIn<Note>("0000", "删除成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * 打开修改页面
	 */
	@RequestMapping("/noteController_openUpdatePage")
	public String openUpdatePage(@RequestParam("id") Long id, Model model) {
		Note note = noteRepository.findOne(id);
		MsgIn<Note> in = new MsgIn<Note>(note);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("note", out.getTobj());
		return "note/noteUpdate";
	}

	@RequestMapping("/noteController_openEdit")
	public String openEdit(@RequestParam("id") Long id, Model model){
		Note note = noteRepository.findOne(id);
		MsgIn<Note> in = new MsgIn<Note>(note);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("note", out.getTobj());
		return "note/noteUpdate";
	}

}
