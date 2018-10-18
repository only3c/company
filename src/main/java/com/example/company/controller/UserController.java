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
import com.example.company.dto.LoginUserDTO;
import com.example.company.dto.UserDTO;
import com.example.company.model.Dictionary;
import com.example.company.model.InstInfo;
import com.example.company.model.Role;
import com.example.company.model.User;
import com.example.company.repository.DictionaryRepository;
import com.example.company.repository.InstInfoRepository;
import com.example.company.repository.RoleRepository;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.UserService;
import com.example.company.util.Constant;
import com.example.company.util.DateUtil;
import com.example.company.util.MD5Util;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;
import com.example.company.util.StringUtils;

@Controller
public class UserController extends BaseController {

	private static final String ERROR_CODE = "1005";
	private static final String ERROR_MESSAGE = "不是管理员不能批量更改密码";
	private static final String DEFAULT_PASSWORD = "1234";
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;

	/**
	 * 查询部门树
	 */
	@RequestMapping("/userController_openUserTree")
	public String openAddUserTree() {
		return "user/userlist";
	}

	/**
	 * 获取用户列表
	 */
	@RequestMapping("/userController_userList")
	public void userList(@RequestParam(value = "usercode", required = false) String usercode,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "instcode", required = false) String instcode, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response, HttpSession session) {
		Pageable pageable = new PageRequest(page - 1, rows);
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		if (StringUtils.isBlank(instcode)) {
			instcode = user.getInstcode();
		}
		Page<User> userList = userService.selectUserPageByUsercodeAndInstCode(
				username,usercode, instcode, pageable);
		System.out.println("总条数：" + userList.getTotalElements());
		System.out.println("总页数：" + userList.getTotalPages());
		if (userList != null && userList.getSize() > 0) {
			PageUtils<User> pages = new PageUtils<User>(Integer.valueOf((int) userList.getTotalElements()),
					page, rows, userList.getContent());
			// 封装返回信息
			Map<String, Object> result = pages.getResult(userList.getContent());
			MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(result);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);
			String retString = out.getTjson();
			writeResponse(response, retString);
		}
		return;
	}

	/**
	 * 打开浏览用户信息页
	 * 
	 * @param usercode
	 * @param model
	 * @return
	 */
	@RequestMapping("/userController_openUserPage")
	public String openInstInfoPage(@RequestParam("usercode") String usercode, Model model) {
		User user = userService.selectUserByUsercode(usercode);
		MsgIn<User> in = new MsgIn<User>(user);
		String retValue = in.getJsonstring();
		MsgOut<Object> out = new MsgOut<Object>(retValue);
		model.addAttribute("userinfo", out.getTobj());
		return "user/userView";
	}

	/**
	 * 打开新增用户页
	 * 
	 * @param instcode
	 * @param model
	 * @return
	 */
	@RequestMapping("/userController_openAddPage")
	public String openAddPage(@RequestParam(value = "instcode", required = false) String instcode, Model model) {
		if (StringUtils.isNotBlank(instcode)) {
			InstInfo instInfo = instInfoRepository.findOneByInstcode(instcode);
			MsgIn<InstInfo> in1 = new MsgIn<InstInfo>(instInfo);
			MsgOut<Object> out1 = new MsgOut<Object>(in1.getJsonstring());
			List<Role> role = roleRepository.findAll();
			MsgIn<List<Role>> in2 = new MsgIn<List<Role>>(role);
			MsgOut<Object> out2 = new MsgOut<Object>(in2.getJsonstring());
			model.addAttribute("userinfo", out1.getTobj());
			model.addAttribute("role", out2.getTobj());
		}
		return "user/userAdd";
	}

	/**
	 * 新增用户code验证
	 */
	@RequestMapping("/userController_usercodeCheck")
	private void createUserCheck(@RequestParam(value = "usercode") String usercode, HttpServletResponse response) {
		User user = userService.selectUserByUsercode(usercode);
		if (user!=null) 
			writeResponse(response, "false");
		writeResponse(response, "true");
	}

	/**
	 * 新建用户create
	 */
	@RequestMapping("/userController_createUser")
	public void createUser(@ModelAttribute UserDTO userDTO, HttpSession session, HttpServletResponse response,
			@RequestParam("userroles") String userroles) {
		LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		userDTO.setOpinstcode(loginUserDTO.getInst().getInstcode());
		userDTO.setOprcode(loginUserDTO.getUsercode());
		userDTO.setOpdate(DateUtil.getDateTime(DateUtil.DEFAULT_DATETIME_FORMAT));
		String md5Password = MD5Util.getMD5ofStr("123456");
		userDTO.setPassword(md5Password);
		userDTO.setUserflag("1");
		
		User user =null;
		try {
			BeanUtils.copyProperties(user = new User(), userDTO);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Dictionary dictionary = dictionaryRepository.findOneByDatatypeAndDatacode("certtype",userDTO.getCerttype());
		if(dictionary!=null){
			user.setCerttypename(dictionary.getDataname());
		}
		user.setUserflagname("正常");
		user.setInst(instInfoRepository.findOne(userDTO.getInstcode()));
		user = userRepository.save(user);
		if (user!=null) {
			// 删除权限
			if(user.getRoles()!=null){
				user.getRoles().clear();
			}
			// 添加角色
			List<Role> roleList = new ArrayList<>();
			String roleName="";
			for (String userrole : userroles.split(",")) {
				Role role = roleRepository.findOne(userrole);
				if(role!=null){
					roleName=roleName+role.getRolename()+",";
					roleList.add(role);
				}
			}
			user.setRoles(roleList);
			user.setRolename(roleName.substring(0,roleName.length()-1));
			user.setInstname(user.getInst().getInstname());
			user = userRepository.save(user);
		}

		MsgIn<User> in = new MsgIn<User>(user);
		writeResponse(response, in.getJsonstring());
	}

	/**
	 * 更新用户update
	 */
	@RequestMapping("/userController_updateUser")
	public void updateUser(@ModelAttribute UserDTO userDTO, HttpSession session, HttpServletResponse response,
			@RequestParam("userroles") String userroles) {
		// 登陆用户的信息
		LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		User user = userRepository.findOne(userDTO.getUsercode());
		
		if (user!=null) {			
			//update
			user.setOprcode(loginUserDTO.getInstcode());
			user.setOpdate(DateUtil.getDateTime(DateUtil.DEFAULT_DATETIME_FORMAT));
			user.setUsername(userDTO.getUsername());
			user.setMobilephone(userDTO.getMobilephone());
			user.setCertno(userDTO.getCertno());
			user.setTelno(userDTO.getTelno());
			user.setQq(userDTO.getQq());
			user.setEmail(userDTO.getEmail());
			user.setAddress(userDTO.getAddress());
			user.setPostcode(userDTO.getPostcode());
			// 先删除所有用户权限
			if(user.getRoles()!=null){
				user.getRoles().clear();
			}
			// 添加用户角色确认代码
			List<Role> roleList = new ArrayList<>();
			String roleName="";
			for (String userrole : userroles.split(",")) {
				Role role = roleRepository.findOne(userrole);
				if(role!=null){
					roleName=roleName+role.getRolename()+",";
					roleList.add(role);
				}
			}
			user.setRolename(roleName.substring(0,roleName.length()-1));
			user.setRoles(roleList);
			user = userRepository.save(user);
		}
		MsgIn<User> in = new MsgIn<User>(user);

		writeResponse(response, in.getJsonstring());
	}

	/**
	 * 注销用户信息
	 */
	@RequestMapping("/userController_logoffUser")
	public void logoffInstInfo(@RequestParam("usercode") String usercode, HttpServletResponse response) {
		User user = userRepository.findOne(usercode);
		if(user!=null){
			user.setUserflag("2");
			user.setUserflagname("注销");
		}
		user = userRepository.save(user);
		MsgIn<User> in = new MsgIn<User>(user);
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 恢复用户信息
	 */
	@RequestMapping("/userController_recoverUser")
	public void recoverInstInfo(@RequestParam("usercode") String usercode, HttpServletResponse response) {
		User user = userRepository.findOne(usercode);
		if(user!=null){
			user.setUserflag("1");
			user.setUserflagname("正常");
		}
		user = userRepository.save(user);
		MsgIn<User> in = new MsgIn<User>(user);
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/userController_deleteUser")
	public void deleteInstInfo(@RequestParam("usercode") String usercodes, HttpServletResponse response) {
		String[] usercodeArray = usercodes.split(",");
		for (String usercode : usercodeArray) {
			if (StringUtils.isNotBlank(usercode)) {
				userRepository.delete(usercode);
			}
		}
		MsgIn<String> in = new MsgIn<String>("");
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 修改用户信息
	 */
	@RequestMapping("/userController_openUpdatePage")
	public String openUpdatePage(@RequestParam("usercode") String usercode, Model model) {
		
		User user = userService.selectUserByUsercode(usercode);
		MsgIn<User> in1 = new MsgIn<User>(user);
		MsgOut<Object> out1 = new MsgOut<Object>(in1.getJsonstring());
		
		MsgIn<List<Role>> in2 = new MsgIn<List<Role>>(user.getRoles());
		MsgOut<Object> out2 = new MsgOut<Object>(in2.getJsonstring());		
		
		model.addAttribute("userinfo", out1.getTobj());
		model.addAttribute("role", out2.getTobj());
		return "user/userUpdate";
	}

	/**
	 * 打开密码重置页
	 */
	@RequestMapping("/userController_resetPwd")
	public String toResetPage(@RequestParam("userstr") String usercode, Model model) {
		User user = userService.selectUserByUsercode(usercode);
		MsgIn<User> in = new MsgIn<User>(user);
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
		model.addAttribute("userinfo", out.getTobj());
		return "user/changePassword";
	}

	/**
	 * 用户密码重置
	 */
	@RequestMapping("/userController_savePassword")
	public void resetPwd(@RequestParam("usercode") String usercode, HttpServletResponse response,
			HttpServletRequest request) {
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("usercode", usercode);
		jsonMap.put("password", password);
		jsonMap.put("newpassword", newpassword);

		MsgIn<User> in;
		User user = userService.selectUserByUsercode(usercode);
		if (user == null) {
			in = new MsgIn<User>("1001", "用户不存在");
		} else {
			user.setPassword(newpassword==null?MD5Util.getMD5ofStr("1234"):MD5Util.getMD5ofStr(newpassword));
			user = userRepository.save(user);
		}
		in = new MsgIn<User>("0000", "密码重置成功");
		String retString = in.getJsonstring();
		writeResponse(response, retString);
		return;
	}

	/**
	 * 管理员多选用户密码重置
	 */
	@RequestMapping("/userController_adminResetPwd")
	public void resetListPwd(@RequestParam("usercode") String usercodes, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) {
		LoginUserDTO loginUserDTO = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		String retString = "";
		// 只有超级管理员有权多人一次性还原密码
		if (loginUserDTO != null && loginUserDTO.getUsercode().equals("admin")) {
			if (usercodes != null) {
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("usercodes", usercodes);
				jsonMap.put("newpassword", DEFAULT_PASSWORD);
				retString = "";
			}
			
			MsgIn<User> in;
			for (String usercode : usercodes.split(",")) {
				User user = userService.selectUserByUsercode(usercode);
				if (user == null) {
					in = new MsgIn<User>("1001", "用户不存在");
				} else {
					user.setPassword(MD5Util.getMD5ofStr("123456"));
					user = userRepository.save(user);
				}
			}
			
			in = new MsgIn<User>("0000", "密码重置成功");
			retString = in.getJsonstring();
		} else {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put(ERROR_CODE, ERROR_MESSAGE);
			retString = JSON.toJSONString(returnMap);
		}
		// 密码重置
		writeResponse(response, retString);
		return;
	}
}
