package com.example.company.controller;

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

import com.example.company.dto.LoginUserDTO;
import com.example.company.model.Holiday;
import com.example.company.model.Role;
import com.example.company.repository.DictionaryRepository;
import com.example.company.repository.HolidayRepository;
import com.example.company.repository.InstInfoRepository;
import com.example.company.repository.RoleRepository;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.HolidayService;
import com.example.company.util.Constant;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class HolidayController extends BaseController {

	@Autowired
	private HolidayRepository holidayRepository;
	@Autowired
	private HolidayService holidayService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;

	/**
	 * 打开部门请假列表页
	 */
	@RequestMapping("/holidayController_openList")
	public String openList() {
		return "holiday/holidayList";
	}

	/**
	 * 获取请假分页列表
	 */
	@RequestMapping("/holidayController_holidayList")
	public void holidayList(@RequestParam(value = "holidaytype", required = false) String holidaytype,
			@RequestParam(value = "flowtype", required = false) String flowtype,
			@RequestParam(value = "cause", required = false) String cause,
			@RequestParam(value = "username", required = false) String username, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response, HttpSession session) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Holiday> dicList = null;
		List<Role> roleList = userRepository.findOne(user.getUsercode()).getRoles();
		if (roleList.contains(roleRepository.findOne("1")))
			dicList = holidayService.getHolidayPageByInstcode(user.getInstcode(), holidaytype, flowtype, cause,
					username, pageable);
		else
			dicList = holidayService.getHolidayPageByUsercode(user.getUsercode(), holidaytype, flowtype, cause,
					pageable);
		System.out.println("总条数：" + dicList.getTotalElements());
		System.out.println("总页数：" + dicList.getTotalPages());
		if (dicList != null && dicList.getSize() > 0) {
			PageUtils<Holiday> pages = new PageUtils<Holiday>(Integer.valueOf((int) dicList.getTotalElements()), page,
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
	 * 打开新增请假页
	 */
	@RequestMapping("/holidayController_openAddPage")
	public String openAddPage() {
		return "holiday/holidayAdd";
	}

	/**
	 * 保存请假信息
	 */
	@RequestMapping("/holidayController_saveHoliday")
	public void saveHoliday(@ModelAttribute Holiday holiday, HttpSession session, HttpServletResponse response) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		holiday.setFlowtype("2");
		holiday.setFlowtypename(dictionaryRepository.findOneByDatatypeAndDatacode("flowtype", "2").getDataname());
		holiday.setHolidaytypename(dictionaryRepository
				.findOneByDatatypeAndDatacode("holidaytype", holiday.getHolidaytype()).getDataname());
		holiday.setInstcode(user.getInstcode());
		holiday.setInstname(instInfoRepository.findOne(user.getInstcode()).getInstname());
		holiday.setUsercode(user.getUsercode());
		holiday.setUsername(userRepository.findOne(user.getUsercode()).getUsercode());
		holiday = holidayRepository.save(holiday);
		MsgIn<Holiday> in = new MsgIn<Holiday>("0000", "更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 修改请假信息
	 */
	@RequestMapping("/holidayController_updateHoliday")
	public void updateHoliday(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		Long id = Long.valueOf(request.getParameter("id"));
		Holiday holiday = holidayRepository.findOne(id);
		if (holiday != null) {
			holiday = holidayRepository.save(holiday);
		}
		MsgIn<Holiday> in = new MsgIn<Holiday>("0000", "更新成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 删除请假
	 */
	@RequestMapping("/holidayController_deleteHolidays")
	public void deleteHolidays(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			holidayRepository.delete(Long.valueOf(id));
		}
		MsgIn<Holiday> in = new MsgIn<Holiday>("0000", "删除成功");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * agree
	 */
	@RequestMapping("/holidayController_agreeHolidays")
	public void agreeHolidays(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			Holiday holiday = holidayRepository.findOne(Long.valueOf(id));
			holiday.setFlowtype("3");
			holiday.setFlowtypename("通过");
			holidayRepository.save(holiday);
		}
		MsgIn<Holiday> in = new MsgIn<Holiday>("0000", "SUCCESS");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * refuse
	 */
	@RequestMapping("/holidayController_refuseHolidays")
	public void refuseHolidays(@RequestParam("ids") String ids, HttpServletResponse response) {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			Holiday holiday = holidayRepository.findOne(Long.valueOf(id));
			holiday.setFlowtype("4");
			holiday.setFlowtypename("未通过");
			holidayRepository.save(holiday);
		}
		MsgIn<Holiday> in = new MsgIn<Holiday>("0000", "SUCCESS");
		MsgOut<Object> out = new MsgOut<>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}

	/**
	 * 打开修改页面
	 */
	@RequestMapping("/holidayController_openUpdatePage")
	public String openUpdatePage(@RequestParam("id") Long id, Model model) {
		Holiday holiday = holidayRepository.findOne(id);
		MsgIn<Holiday> in = new MsgIn<Holiday>(holiday);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());

		model.addAttribute("holiday", out.getTobj());
		return "holiday/holidayUpdate";
	}

}
