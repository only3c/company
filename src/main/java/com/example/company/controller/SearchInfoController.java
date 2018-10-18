package com.example.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchInfoController {

	/**
	 * 查询部门树 查user
	 */
	@RequestMapping("/searchInfoController_openUserTree")
	public String openAddUserTree() {
		return "search/userlist";
	}
	
	
	/**
	 * 打开部门列表页 查inst
	 */
	@RequestMapping("/searchInfoController_openInstTree")
	public String openInstTree() {
		return "search/instlist";
	}
}
