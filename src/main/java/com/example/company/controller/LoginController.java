package com.example.company.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.example.company.dto.LoginUserDTO;
import com.example.company.model.Menu;
import com.example.company.model.Role;
import com.example.company.model.User;
import com.example.company.serveice.UserService;
import com.example.company.util.CaptchaUtil;
import com.example.company.util.Constant;
import com.example.company.util.DateUtil;
import com.example.company.util.MD5Util;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.MsgHeadUtils;
import com.example.company.util.StringUtils;

@Controller
public class LoginController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	private static final String ERROR_PASSWORD_CODE = "1002";
	private static final String ERROR_USERFLAG_CODE = "1003";
	private static final String ERROR_CHKNUMBER_CODE = "1004";
	private static final String ERROR_OVERTIME_CODE = "1005";

	private static final String ERROR_PASSWORD_MESSAGE = "您输入的密码错误！";
	private static final String ERROR_USERFLAG_MESSAGE = "该用户已经被注销，请联系管理员！";
	private static final String ERROR_CHKNUMBER_MESSAGE = "您输入的验证码错误！";
	private static final String ERROR_OVERTIME_MESSAGE = "验证码超时，请重新输入！";

	private static final long CAPTCHA_VALID_TIME = 5 * 60 * 1000; // 图形验证码有效时间5分钟

	@Autowired
	private UserService userService;

	/**
	 * 加载登录页面
	 */
	@RequestMapping("/login")
	public String openPrLogin() {
		return "login";
	}

	@RequestMapping("/checkUser")
	public void ajaxCheckUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 测试用 ---------start--------
		session.setAttribute(Constant.SESSION_CAPTCHA_CODE, "1111");
		session.setAttribute(Constant.SESSION_CAPTCHA_VAILD, DateUtil.getDateTime());
		// 测试用 ---------end--------

		String sessionChkcode = (String) session.getAttribute(Constant.SESSION_CAPTCHA_CODE);
		String sessionCaptchaTime = (String) session.getAttribute(Constant.SESSION_CAPTCHA_VAILD);
		// 用户id
		String usercode = request.getParameter("usercode");
		// 验证码
		String checkcode = request.getParameter("checkcode");
		// 密码
		String password = request.getParameter("password");
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (null == sessionChkcode || !sessionChkcode.equals(checkcode.toUpperCase())) {
			returnMap.put(MsgHeadUtils.RESPONSECCODE, ERROR_CHKNUMBER_CODE);
			returnMap.put(MsgHeadUtils.RESPONSSSAGE, ERROR_CHKNUMBER_MESSAGE);
			writeResponse(response, JSON.toJSONString(returnMap));
			return;
		}
		if (DateUtil.diffSeconds(DateUtil.getNow(),
				DateUtil.parseToDateTime(sessionCaptchaTime)) <= CAPTCHA_VALID_TIME) {
			// 根据用户名，查询用户信息
			User user = userService.selectUserByUsercode(usercode);
			// 判断用户状态
			if (user == null) {
				returnMap.put(MsgHeadUtils.RESPONSECCODE, ERROR_PASSWORD_CODE);
				returnMap.put(MsgHeadUtils.RESPONSSSAGE, ERROR_PASSWORD_MESSAGE);
			}
			if (user.getUserflag() != null && ("1").equals(user.getUserflag())) {
				if (password != null && MD5Util.getMD5ofStr(password).equals(user.getPassword()) && usercode != null
						&& usercode.equals(user.getUsercode())) {
					LoginUserDTO loginUser = new LoginUserDTO();
					loginUser.setUserflag(user.getUserflag());
					loginUser.setUsercode(usercode);
					loginUser.setInstcode(user.getInst().getInstcode());
					loginUser.setUsername(user.getUsername());
					loginUser.setInst(user.getInst());
					loginUser.setRoles(user.getRoles());
					// 将用户信息放入session
					session.setAttribute(Constant.SESSION_USER_KEY, loginUser);

					returnMap.put(MsgHeadUtils.RESPONSECCODE, "0000"); // 用户校验成功
					LOGGER.info(StringUtils.makeLogStr("用户[", loginUser.getUsername(), "] 已经登录"));
				} else {
					returnMap.put(MsgHeadUtils.RESPONSECCODE, ERROR_PASSWORD_CODE);
					returnMap.put(MsgHeadUtils.RESPONSSSAGE, ERROR_PASSWORD_MESSAGE);
				}
			} else {
				returnMap.put(MsgHeadUtils.RESPONSECCODE, ERROR_USERFLAG_CODE);
				returnMap.put(MsgHeadUtils.RESPONSSSAGE, ERROR_USERFLAG_MESSAGE);
			}
		} else {
			returnMap.put(MsgHeadUtils.RESPONSECCODE, ERROR_OVERTIME_CODE);
			returnMap.put(MsgHeadUtils.RESPONSSSAGE, ERROR_OVERTIME_MESSAGE);
		}
		MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(returnMap);
		String retValue = in.getJsonstring();
		MsgOut<Object> out = new MsgOut<Object>(retValue);
		String retString = out.getTjson();
		writeResponse(response, retString);
	}

	/**
	 * 获取校验码图片
	 */
	@RequestMapping("/checkNumber")
	public void generateChkNumber(HttpServletResponse response, HttpSession session) throws IOException {
		CaptchaUtil captUtil = CaptchaUtil.Instance();
		ByteArrayInputStream bi = captUtil.getImage();
		ServletOutputStream out = response.getOutputStream();
		int temp = 0;
		while ((temp = bi.read()) != -1) {
			out.write(temp);
		}
		out.flush();
		out.close();
		session.setAttribute(Constant.SESSION_CAPTCHA_CODE, captUtil.getChkcode());
		session.setAttribute(Constant.SESSION_CAPTCHA_VAILD, DateUtil.getDateTime());
	}

	/**
	 * 登录后根据权限构建菜单
	 */
	@RequestMapping("/login_login")
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		LoginUserDTO lui = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		String usercode = lui.getUsercode();
		if (lui != null && !"".equals(usercode.trim())) {
			// 根据usercode获取用户信息和对应的角色
			User user = userService.selectUserByUsercode(usercode);
			if (user == null) {
				LOGGER.info("错误码：" + "1111" + "  错误信息：" + "用户不存在");
				session.setAttribute("errorCode", "1111");
				session.setAttribute("errorMessage", "用户不存在");
				return "error";
			}

			if (null != lui) {
				lui.setRoles(user.getRoles());
				// 将完整的用户信息放入session
				session.setAttribute(Constant.SESSION_USER_KEY, lui);
				request.setAttribute("menuList", getMenus(user));
			}
			return "login/main";
		} else {
			LOGGER.info("错误码：" + "1111" + "  错误信息：" + "用户不存在！");
			session.setAttribute("errorCode", "1111");
			session.setAttribute("errorMessage", "用户不存在！");
			return "error";
		}
	}

	/**
	 * 打开首页
	 */
	@RequestMapping("login_openWelcomePage")
	public String openWelcomePage() {
		return "login/welcome";
	}

	/**
	 * 退出登录
	 */
	@RequestMapping("login_logout")
	public String logout(HttpServletRequest request, HttpSession session) {
		/*
		 * 注销用户session
		 */
		session.removeAttribute(Constant.SESSION_USER_KEY);
		request.setAttribute("title", "退出成功");
		request.setAttribute("message", "您已成功退出，感谢您使用连队管理系统综合平台！");
		return "login/logout";
	}

	/**
	 * 获取用户对应的菜单
	 */
	public List<Menu> getMenus(User lui) {

		List<Role> roleList = lui.getRoles();
		if (roleList == null || roleList.size() == 0) {
			LOGGER.info("该用户暂时没有角色信息！");
			return null;
		}
		// 返回菜单信息
		List<Menu> menues = new ArrayList<>();
		for (Role role : roleList) {
			menues.addAll(role.getMenus());
		}
		return menues;
	}
}
