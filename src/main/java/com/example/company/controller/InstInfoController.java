package com.example.company.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.example.company.dto.InstDTO;
import com.example.company.dto.InstTreeDTO;
import com.example.company.dto.LoginUserDTO;
import com.example.company.model.InstInfo;
import com.example.company.model.Role;
import com.example.company.repository.DictionaryRepository;
import com.example.company.repository.InstInfoRepository;
import com.example.company.serveice.InstInfoService;
import com.example.company.serveice.impl.InstCodeServiceImpl;
import com.example.company.util.Constant;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class InstInfoController extends BaseController {


	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private InstInfoService instInfoService;
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private InstCodeServiceImpl instCodeServiceImpl;

	/**
	 * 打开部门列表页
	 */
	@RequestMapping("/instInfoController_openInstTree")
	public String openInstTree() {
		return "instinfo/instlist";
	}

	/**
	 * 获取部门树
	 */
	@RequestMapping("/getTreeNode")
	public void generateTreeNode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String id = request.getParameter("id");
		// 获取根节点信息
		LoginUserDTO lui = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);

		String littleInst = "";
		List<Role> roleList = lui.getRoles();
		boolean hasMngFlag = false;
		if (roleList != null && roleList.size() > 0) {
			for (Role r : roleList) {
				String instclass = r.getInstclass();
				if (instclass.equals("0")) {
					hasMngFlag = true;
					break;
				}
			}
		}
		if (lui != null) {
			if (StringUtils.isBlank(id)) {
				if (hasMngFlag) {
					id = "";
				} else {
					id = lui.getInst().getPinstinfo().getInstcode();
					littleInst = lui.getInst().getInstcode();
				}

			}
		}
		List<InstInfo> instInfos = new ArrayList<>();
		if ((id == null || id == "") && (littleInst == null || littleInst == "")) {
			instInfos = instInfoRepository.findListByPinstinfo_instcodeIsNull();
		} else if ((id != null && id != "") && (littleInst != null && littleInst != "")) {
			instInfos = instInfoRepository.findListByInstcodeAndPinstinfo_instcode(littleInst, id);
		} else if ((id == null || id == "") && (littleInst != null && littleInst != "")) {
			instInfos = instInfoRepository.findListByInstcode(littleInst);
		} else if ((id != null && id != "") && (littleInst == null || littleInst == "")) {
			instInfos = instInfoRepository.findListByPinstinfo_instcode(id);
		}

		// 封装部门树
		List<InstTreeDTO> treeList = new ArrayList<InstTreeDTO>();
		if (instInfos != null && instInfos.size() > 0) {
			for (InstInfo instInfo : instInfos) {
				String instcode = instInfo.getInstcode();
				InstTreeDTO orgtree = new InstTreeDTO();
				orgtree.setId(instInfo.getInstcode());
				orgtree.setText(instInfo.getInstname());

				List<InstInfo> cInstInfos = instInfoRepository.findListByPinstinfo_instcode(instcode);
				if (cInstInfos != null && cInstInfos.size() > 0) {
					orgtree.setState("closed"); 
				} else {
					orgtree.setState("open"); 
				}
				treeList.add(orgtree); 
			}
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("root", treeList);
		writeResponse(response, JSON.toJSONString(returnMap));
		return;
	}

	/**
	 * 获取部门分页列表
	 */
	@RequestMapping("/instInfoController_instList")
	public void instList(@RequestParam(value = "parentcode", required = false) String parentcode,
			@RequestParam(value = "instname", required = false) String instname,
			@RequestParam(value = "instcode", required = false) String instcode, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response,HttpSession session) {
		LoginUserDTO lui = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		if(parentcode==null) parentcode=lui.getInstcode();
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<InstInfo> instInfoPage = instInfoService.selectInstInfoPage(parentcode, instcode, instname, pageable);
		System.out.println("总条数：" + instInfoPage.getTotalElements());
		System.out.println("总页数：" + instInfoPage.getTotalPages());
		if (instInfoPage != null && instInfoPage.getSize() > 0) {
			PageUtils<InstInfo> pages = new PageUtils<InstInfo>(Integer.valueOf((int) instInfoPage.getTotalElements()),
					page, rows, instInfoPage.getContent());
			// 封装返回信息
			List<InstInfo> instInfoList = instInfoPage.getContent();
			Map<String, Object> result = pages.getResult(instInfoList);
			MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(result);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);
			String retString = out.getTjson();
			writeResponse(response, retString);
		}
		return;

	}

	/**
	 * 打开浏览部门信息页
	 */
	@RequestMapping("/instInfoController_openInstInfoPage")
	public String openInstInfoPage(@RequestParam("instcode") String instcode, Model model) {
		if (StringUtils.isNotBlank(instcode)) {
			InstInfo instInfo = instInfoRepository.findOneByInstcode(instcode);
			if (instInfo != null) {
				MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
				MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
				model.addAttribute("instinfo", out.getTobj());
			}
		}
		return "instinfo/instView";
	}

	/**
	 * 打开新增部门页
	 */
	@RequestMapping("/instInfoController_openAddPage")
	public String openAddPage(@RequestParam(value = "parentcode", required = false) String parentcode, Model model) {
		if (StringUtils.isNotBlank(parentcode)) {
			InstInfo instInfo = instInfoRepository.findOneByInstcode(parentcode);
			if (instInfo != null) {
				MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
				MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
				model.addAttribute("instinfo", out.getTobj());
			}
		}
		return "instinfo/instAdd";
	}

	/**
	 * 保存部门信息
	 */
	@RequestMapping("/instInfoController_saveInstInfo")
	public void saveInstInfo(@ModelAttribute InstDTO instDTO, HttpSession session, HttpServletResponse response) {
		LoginUserDTO lui = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		instDTO.setOpinstcode(lui.getInst().getInstcode());
		instDTO.setOprcode(lui.getUsercode());
		InstInfo instInfo = null;
		if (instDTO.getInstcode() == null && !"".equals(instDTO.getInstcode())) {
			instInfo = new InstInfo();
			try {
				BeanUtils.copyProperties(instInfo, instDTO);
				instInfo.setInstflag("1");
				if (instDTO.getPinstcode() != null && !"".equals(instDTO.getPinstcode())) {
					instInfo.setPinstinfo(instInfoRepository.findOne(instDTO.getPinstcode()));
				}
				instInfo.setInstclassname(dictionaryRepository
						.findOneByDatatypeAndDatacode("instclass", instInfo.getInstclass()).getDataname());
				instInfo.setCityname(dictionaryRepository
						.findOneByDatatypeAndDatacodeAndParentcode("city", instInfo.getCity(), instInfo.getProvince())
						.getDataname());
				instInfo.setProvincename(dictionaryRepository
						.findOneByDatatypeAndDatacode("province", instInfo.getProvince()).getDataname());
				instInfo.setInstflagname(dictionaryRepository
						.findOneByDatatypeAndDatacode("logoffflag", instInfo.getInstflag()).getDataname());

			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			instInfo.setInstcode(instCodeServiceImpl.makeCode(instDTO.getProvince(), instDTO.getCity(),
					instDTO.getInstclass()));
			instInfo = instInfoRepository.save(instInfo);
		} else {
			instInfo = instInfoRepository.findOne(instDTO.getInstcode());
			instInfo.setInstname(instDTO.getInstname());
			instInfo.setInstsname(instDTO.getInstsname());
			instInfo.setTelno(instDTO.getTelno());
			instInfo.setContactname(instDTO.getContactname());
			instInfo.setContacttelno(instDTO.getContacttelno());
			instInfo.setCityname(dictionaryRepository
					.findOneByDatatypeAndDatacodeAndParentcode("city", instDTO.getCity(), instDTO.getProvince())
					.getDataname());
			instInfo.setProvincename(
					dictionaryRepository.findOneByDatatypeAndDatacode("province", instDTO.getProvince()).getDataname());
			instInfo = instInfoRepository.save(instInfo);
		}
		MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
		writeResponse(response, in.getJsonstring());
	}

	/**
	 * 获取部门信息
	 */
	@RequestMapping("instInfoController_getInstInfo")
	public void openInstInfoPage(@RequestParam("instcode") String instcode, HttpServletResponse response) {
		InstInfo instInfo = instInfoRepository.findOneByInstcode(instcode);
		if (instInfo != null) {
			MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
			MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
			writeResponse(response, out.getTjson());
		}
	}

	/**
	 * 注销部门信息
	 */
	@RequestMapping("/instInfoController_logoffInstInfo")
	public void logoffInstInfo(@RequestParam("instcode") String instcode, HttpServletResponse response) {
		InstInfo instInfo = instInfoRepository.findOne(instcode);
		if (instInfo != null) {
			instInfo.setInstflag("2");
			instInfo.setInstflagname("注销");
		}
		instInfo = instInfoRepository.save(instInfo);
		MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 恢复部门信息
	 */
	@RequestMapping("/instInfoController_recoverInstInfo")
	public void recoverInstInfo(@RequestParam("") String instcode, HttpServletResponse response) {
		InstInfo instInfo = instInfoRepository.findOne(instcode);
		if (instInfo != null) {
			instInfo.setInstflag("1");
			instInfo.setInstflagname("正常");
		}
		instInfo = instInfoRepository.save(instInfo);
		MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 删除部门
	 */
	@RequestMapping("/instInfoController_deleteInstInfo")
	public void deleteInstInfo(@RequestParam("instcode") String instcodes, HttpServletResponse response) {
		String[] instcodeArray = instcodes.split(",");
		for (String instcode : instcodeArray) {
			if (StringUtils.isNotBlank(instcode)) {
				deleteCinst(instInfoRepository.findOne(instcode));
				instInfoRepository.delete(instcode);
			}
		}
		MsgIn<String> in = new MsgIn<String>("");
		writeResponse(response, in.getJsonstring());
		return;
	}
	
	private void deleteCinst(InstInfo instInfo){
		List<InstInfo> list = instInfoRepository.findListByPinstinfo_instcode(instInfo.getInstcode());
		if(list!=null&&list.size()>0){
			for(InstInfo cinstInfo :list){
				deleteCinst(cinstInfo);
			}
		}else{
			instInfoRepository.delete(instInfo);
		}
	}

	@RequestMapping("/instInfoController_openUpdatePage")
	public String openUpdatePage(@RequestParam("instcode") String instcode, Model model) {
		if (StringUtils.isNotBlank(instcode)) {
			InstInfo instInfo = instInfoRepository.findOneByInstcode(instcode);
			if (instInfo != null) {
				MsgIn<InstInfo> in = new MsgIn<InstInfo>(instInfo);
				MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
				model.addAttribute("instinfo", out.getTobj());
			}
		}
		return "instinfo/instUpdate";
	}
}
