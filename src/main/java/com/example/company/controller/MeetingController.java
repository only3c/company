package com.example.company.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.company.dto.LoginUserDTO;
import com.example.company.dto.MeetingDTO;
import com.example.company.model.FileStorage;
import com.example.company.model.Meeting;
import com.example.company.repository.DictionaryRepository;
import com.example.company.repository.InstInfoRepository;
import com.example.company.repository.MeetingRepository;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.FileUploadService;
import com.example.company.serveice.MeetingService;
import com.example.company.util.Constant;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class MeetingController extends BaseController {

	@Autowired
	private MeetingRepository meetingRepository;
	@Autowired
	private MeetingService meetingService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;

	/**
	 * 打开部门会议列表页
	 */
	@RequestMapping("/meetingController_openList")
	public String openList() {
		return "meeting/meetingList";
	}

	/**
	 * 获取会议分页列表
	 */
	@RequestMapping("/meetingController_meetingList")
	public void meetingList(@RequestParam(value = "meetingtype", required = false) String meetingtype,
			@RequestParam(value = "flowtype", required = false) String flowtype,
			@RequestParam(value = "summary", required = false) String summary,
			@RequestParam(value = "username", required = false) String username, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response, HttpSession session) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		Pageable pageable = new PageRequest(page - 1, rows);
		// Page<Meeting> dicList = null;
		// List<Role> roleList =
		// userRepository.findOne(user.getUsercode()).getRoles();
		// if (roleList.contains(roleRepository.findOne("1")))
		Page<Meeting> dicList = meetingService.getMeetingPageByInstcode(user.getInstcode(), meetingtype, flowtype,
				summary, username, pageable);
		// else
		// dicList = meetingService.getMeetingPageByUsercode(user.getUsercode(),
		// meetingtype, flowtype, cause,
		// pageable);
		System.out.println("总条数：" + dicList.getTotalElements());
		System.out.println("总页数：" + dicList.getTotalPages());
		if (dicList != null && dicList.getSize() > 0) {
			PageUtils<Meeting> pages = new PageUtils<Meeting>(Integer.valueOf((int) dicList.getTotalElements()), page,
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
	 * 打开新增会议页
	 */
	@RequestMapping("/meetingController_openAddPage")
	public String openAddPage() {
		return "meeting/meetingAdd";
	}

	/**
	 * 保存会议信息
	 */
	@RequestMapping("/meetingController_saveMeeting")
	public void saveMeeting(@ModelAttribute Meeting meeting, HttpSession session, HttpServletResponse response) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		meeting.setFlowtype("2");
		meeting.setFlowtypename(dictionaryRepository.findOneByDatatypeAndDatacode("flowtype", "2").getDataname());
		meeting.setMeetingtypename(dictionaryRepository
				.findOneByDatatypeAndDatacode("meetingtype", meeting.getMeetingtype()).getDataname());
		meeting.setInstcode(user.getInstcode());
		meeting.setInstname(instInfoRepository.findOne(user.getInstcode()).getInstname());
		meeting.setUsercode(user.getUsercode());
		meeting.setUsername(userRepository.findOne(user.getUsercode()).getUsercode());
		meeting = meetingRepository.save(meeting);
		MsgIn<Meeting> in = new MsgIn<Meeting>("0000", "保存成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 修改会议信息
	 */
	@RequestMapping("/meetingController_updateMeeting")
	public void updateMeeting(@ModelAttribute MeetingDTO dto, HttpServletRequest request, HttpSession session,
			HttpServletResponse response, @RequestParam(value="file",required=false) MultipartFile file) {
		Long id = Long.valueOf(request.getParameter("id"));
		Meeting meeting = meetingRepository.findOne(id);
		if (meeting != null) {
			if(file!=null){
				try{
					FileStorage fileStorage =this.fileUploadService.upload(file);
					if(fileStorage!=null){
						meeting.setFileid(fileStorage.getId());
						meeting.setFilename(fileStorage.getFileName());
					}
				}catch (RuntimeException e) {
					MsgIn<Meeting> in = new MsgIn<Meeting>("1001", e.getMessage());
					MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
					writeFileResponse(response, out.getJsonstring());
					return;
				}
			}
			meeting.setSummary(dto.getSummary() == null ? "" : dto.getSummary());
			meeting.setMeetingroom(dto.getMeetingroom());
			meeting = meetingRepository.save(meeting);
		}
		MsgIn<Meeting> in = new MsgIn<Meeting>("0000", "更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeFileResponse(response, out.getJsonstring());
	}
	
	private void writeFileResponse(HttpServletResponse response, String returnValue) {
		try {
	        response.setContentType("text/html");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out = response.getWriter();
	        out.print(returnValue);
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		return;
		
	}

	/**
	 * 删除会议
	 */
	@RequestMapping("/meetingController_deleteMeetings")
	public void deleteMeetings(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			meetingRepository.delete(Long.valueOf(id));
		}
		MsgIn<Meeting> in = new MsgIn<Meeting>("0000", "删除成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * agree
	 */
	@RequestMapping("/meetingController_agreeMeetings")
	public void agreeMeetings(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			Meeting meeting = meetingRepository.findOne(Long.valueOf(id));
			meeting.setFlowtype("3");
			meeting.setFlowtypename("通过");
			meetingRepository.save(meeting);
		}
		MsgIn<Meeting> in = new MsgIn<Meeting>("0000", "SUCCESS");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * refuse
	 */
	@RequestMapping("/meetingController_refuseMeetings")
	public void refuseMeetings(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			Meeting meeting = meetingRepository.findOne(Long.valueOf(id));
			meeting.setFlowtype("4");
			meeting.setFlowtypename("未通过");
			meetingRepository.save(meeting);
		}
		MsgIn<Meeting> in = new MsgIn<Meeting>("0000", "SUCCESS");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * 打开修改页面
	 */
	@RequestMapping("/meetingController_openUpdatePage")
	public String openUpdatePage(@RequestParam("id") Long id, Model model) {
		Meeting meeting = meetingRepository.findOne(id);
		MsgIn<Meeting> in = new MsgIn<Meeting>(meeting);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("meeting", out.getTobj());
		return "meeting/meetingUpdate";
	}
	

	/**
	 * GETCOMBOX
	 */
	@RequestMapping("/meetingController_getAllByType")
	public void getAllByType(@RequestParam("meetingtype") String meetingtype,  HttpServletResponse response,HttpSession session) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		List<Meeting> meetingList = meetingRepository.findAllByInstcodeAndMeetingtype(user.getInstcode(),meetingtype);
		MsgIn<List<Meeting>> in = new MsgIn<List<Meeting>>(meetingList);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
		writeResponse(response, out.getTjson());
		return;
	}

}
