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
import com.example.company.dto.LoginUserDTO;
import com.example.company.dto.MenuDTO;
import com.example.company.dto.MenuTreeDTO;
import com.example.company.model.Menu;
import com.example.company.model.Role;
import com.example.company.repository.DictionaryRepository;
import com.example.company.repository.InstInfoRepository;
import com.example.company.repository.MenuRepository;
import com.example.company.repository.RoleRepository;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.MenuService;
import com.example.company.serveice.impl.MenuCodeServiceImpl;
import com.example.company.util.Constant;
import com.example.company.util.DateUtil;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class MenuController extends BaseController{
	
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private MenuService menuService;
	@Autowired
	private MenuCodeServiceImpl menuCodeServiceImpl;
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@RequestMapping("/menuController_openMenuMng")
	private String openMenu(){
		return "menu/menulist";	
	}
	/**
	 * 获取菜单tree
	 */
	@RequestMapping("/menuController_generateTreeNode")
	private void generateTreeNode(HttpServletRequest request, HttpServletResponse response, HttpSession session){

		String id = request.getParameter("id");
		//获取根节点信息
		LoginUserDTO lui = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		StringBuffer listMenu = new StringBuffer();

		if (lui != null) {
			if (StringUtils.isBlank(id)) {
				id = "0";
				listMenu = new StringBuffer();
				for (Menu mvo : lui.getMenus()) {
					listMenu.append(mvo.getMenucode()).append(",");
				}
			}
		}
		List<Menu> menuList = menuService.getMenuTree(id, listMenu.toString());
	    List<MenuTreeDTO> treeList = new ArrayList<MenuTreeDTO>();
		for (Menu instMap : menuList) {
			String menucode = instMap.getMenucode();
			MenuTreeDTO menutree = new MenuTreeDTO();
			menutree.setId(menucode);
			menutree.setText(instMap.getMenuname());
			menutree.setMenulevel((Integer)instMap.getMenulevel());
			// 查询该菜单是否有子菜单
			List<Menu> cmenuList = menuRepository.findListByPmenuinfo_menucode(menucode);
			if (cmenuList != null && cmenuList.size() > 0){
				menutree.setState("closed");
			} else {
				menutree.setState("open");
			}
			treeList.add(menutree);
		}
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("root", treeList);
		writeResponse(response, JSON.toJSONString(returnMap));
		return ;
	}
	/**
	 * 获取菜单列表
	 */
	@RequestMapping("/menuController_queryMenuList")
	private void queryMenuList(@RequestParam(value="parentcode", required=false)String parentcode,
			@RequestParam(value="menucode", required=false)String menucode,
			@RequestParam(value="menuname", required=false)String menuname,
			@RequestParam("page")Integer page, 
			@RequestParam("rows")Integer rows, HttpServletResponse response){
		Pageable pageable = new PageRequest(page - 1, rows);
		if (StringUtils.isBlank(parentcode)) {
			parentcode = "0";
		} 	
			Page<Menu> menupage = menuService.selectmenuPage(parentcode, menucode, menuname, pageable);
			System.out.println("总条数：" + menupage.getTotalElements());
			System.out.println("总页数：" + menupage.getTotalPages());
			if (menupage != null && menupage.getSize() > 0) {
				PageUtils<Menu> pages = new PageUtils<Menu>(Integer.valueOf((int) menupage.getTotalElements()),
						page, rows, menupage.getContent());
				// 封装返回信息
				Map<String, Object> result = pages.getResult(menupage.getContent());
				MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(result);
				String retValue = in.getJsonstring();
				MsgOut<Object> out = new MsgOut<Object>(retValue);
				String retString = out.getTjson();
				writeResponse(response, retString);
			}
			return;
	}
	
	/**
	 * 打开添加菜单
	 */
	@RequestMapping("/menuController_openAddPage")
	private String openAddPage(@RequestParam("parentcode")String parentcode, Model model){
		Menu menu = menuRepository.findOne(parentcode);
		if(menu!=null){
			MsgIn<Menu> in = new MsgIn<Menu>(menu);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);		
			Integer menulevel = menu.getMenulevel();
			model.addAttribute("menuinfo",out.getTobj());
			model.addAttribute("menulevel",menulevel+1);
		}
		return "menu/menuAdd";
	}
	
	
	/**
	 * 保存菜单
	 */
	@RequestMapping("/menuController_saveMenu")
	private void saveMenu(@ModelAttribute MenuDTO menuDTO,
			HttpSession session, HttpServletResponse response){
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		menuDTO.setOpinstcode(user.getInst().getInstcode());
		menuDTO.setOprcode(user.getUsercode());
		menuDTO.setOpdate(DateUtil.getDateTime(DateUtil.DEFAULT_DATETIME_FORMAT));
		Menu menu = null;
		if(menuDTO.getMenucode()!=null){//&&! "".equals(menuDTO)
			menu = menuRepository.findOne(menuDTO.getMenucode());
			menu.setMenuname(menuDTO.getMenuname());
			//menu.setDisporder(disporder);
			menu.setDisporder(menuDTO.getDisporder());
			menu.setInstclass(menuDTO.getInstclass());
			menu.setInstclassname(dictionaryRepository
					.findOneByDatatypeAndDatacode("instclass", menuDTO.getInstclass()).getDataname());
			menu.setUrl(menuDTO.getUrl());
			menu.setDescription(menuDTO.getDescription());
		}else{
			try {
				BeanUtils.copyProperties(menu = new Menu(), menuDTO);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			menu.setOpinstname(instInfoRepository.findOne(menuDTO.getOpinstcode()).getInstname());
			menu.setOprname(userRepository.findOne(menuDTO.getOprcode()).getUsername());
			menu.setPmenuinfo(menuRepository.findOne(menuDTO.getParentcode()));
			menu.setIsleafname(dictionaryRepository
					.findOneByDatatypeAndDatacode("yesno", menu.getIsleaf()).getDataname());
			menu.setInstclassname(dictionaryRepository
					.findOneByDatatypeAndDatacode("instclass", menu.getInstclass()).getDataname());
			menu.setMenucode(menuCodeServiceImpl.makeMenuCode(menuDTO.getParentcode(), menuDTO.getMenulevel().toString()));		
		}
		menu = menuRepository.save(menu);
		MsgIn<Menu> in = new MsgIn<Menu>(menu);
		writeResponse(response, in.getJsonstring());
		return;
	}
	
	/**
	 * 打开修改菜单
	 */
	@RequestMapping("/menuController_openUpdatePage")
	private String openUpdatePage(@RequestParam("menucode")String menucode, Model model){
		Menu menu = menuRepository.findOne(menucode);
		if(menu!=null){
			MsgIn<Menu> in = new MsgIn<Menu>(menu);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);		
			model.addAttribute("menuinfo",out.getTobj());
		}
		return "menu/menuUpdate";
	}
	
	/**
	 * 删除菜单信息
	 */
	@RequestMapping("/menuController_deleteMenu")
	private void deleteMenu(@RequestParam("menucodes")String menucodes,HttpServletResponse response){
		String[] menucodearray = menucodes.split(",");
		List<Role> roleList = roleRepository.findAll();
		List<Menu> menuList = menuRepository.findListByMenucodeIn(menucodearray);
		MsgIn<Role> in;
		MsgOut<Object> out;
		
		try{
			if(roleList!=null && roleList.size()>0){
				for(Role role : roleList){
					for(Menu menu : menuList){
						if(role.getMenus()!=null && role.getMenus().size()>0 && role.getMenus().contains(menu)){
							role.getMenus().remove(menu);
							roleRepository.save(role);
						}
					}
					
				}
			}
			menuRepository.delete(menuList);
		}catch (Exception e) {
			in = new MsgIn<Role>("1001","ERROR");
			out = new MsgOut<Object>(in.getJsonstring());
			writeResponse(response, out.getJsonstring());
		}
		in = new MsgIn<Role>("0000","SUUCESS");
		out = new MsgOut<Object>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
		return;
	}
}
