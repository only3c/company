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
import com.example.company.dto.MenuTreeDTO;
import com.example.company.dto.RoleDTO;
import com.example.company.model.Menu;
import com.example.company.model.Role;
import com.example.company.model.User;
import com.example.company.repository.DictionaryRepository;
import com.example.company.repository.InstInfoRepository;
import com.example.company.repository.MenuRepository;
import com.example.company.repository.RoleRepository;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.impl.RoleCodeServiceImpl;
import com.example.company.util.Constant;
import com.example.company.util.DateUtil;
import com.example.company.util.MsgIn;
import com.example.company.util.MsgOut;
import com.example.company.util.PageUtils;

@Controller
public class RoleController extends BaseController {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private DictionaryRepository dictionaryRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstInfoRepository instInfoRepository;
	@Autowired
	private RoleCodeServiceImpl roleCodeServiceImpl;
	@Autowired
	private 
	MenuRepository menuRepository;
	/**
	 * 打开角色列表
	 */
	@RequestMapping("/roleController_openRoleList")
	public String gotoRole() {
		return "role/rolelist";
	}

	/**
	 * 获取角色条件分页列表
	 */
	@RequestMapping("roleController_roleList")
	private void prPubRoleList(@RequestParam(value = "rolecode", required = false) String rolecode,
			@RequestParam(value = "rolename", required = false) String rolename, @RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, HttpServletResponse response) {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Role> roleList = roleRepository.findAllByRolecodeLikeAndRolenameLike(rolecode==null?"%%":"%"+rolecode+"%", rolename==null?"%%":"%"+rolename+"%", pageable);
		System.out.println("总条数：" + roleList.getTotalElements());
		System.out.println("总页数：" + roleList.getTotalPages());
		if (roleList != null && roleList.getSize() > 0) {
			PageUtils<Role> pages = new PageUtils<Role>(Integer.valueOf((int) roleList.getTotalElements()), page, rows,
					roleList.getContent());
			// 封装返回信息
			Map<String, Object> result = pages.getResult(roleList.getContent());
			MsgIn<Map<String, Object>> in = new MsgIn<Map<String, Object>>(result);
			String retValue = in.getJsonstring();
			MsgOut<Object> out = new MsgOut<Object>(retValue);
			String retString = out.getTjson();
			writeResponse(response, retString);
		}
		return;
	}

	/**
	 * 打开添加页
	 * 
	 * @return
	 */
	@RequestMapping("/roleController_openAddPage")
	private String openAddPage() {
		return "role/roleAdd";
	}

	/**
	 * 保存角色信息
	 */
	@RequestMapping("roleController_saveRole")
	private void savePrPubRole(@ModelAttribute RoleDTO roleDTO, HttpSession session, HttpServletResponse response) {
		LoginUserDTO user = (LoginUserDTO) session.getAttribute(Constant.SESSION_USER_KEY);
		roleDTO.setOpinstcode(user.getInst().getInstcode());
		roleDTO.setOprcode(user.getUsercode());
		roleDTO.setOpdate(DateUtil.getDateTime(DateUtil.DEFAULT_DATETIME_FORMAT));
		Role role = null;
		if(roleDTO.getRolecode()!=null && !"".equals(roleDTO.getRolecode())){
			role = roleRepository.findOne(roleDTO.getRolecode());
			role.setRolename(roleDTO.getRolename());
			role.setDescription(roleDTO.getDescription());
		}else{
			role = new Role();
			try {
				BeanUtils.copyProperties(role, roleDTO);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			role.setRolecode(roleCodeServiceImpl.makeRoleCode());
			role.setInstclassname(dictionaryRepository
					.findOneByDatatypeAndDatacode("instclass", role.getInstclass()).getDataname());
			role.setOprname(userRepository.findOne(role.getOprcode()).getUsername());
			role.setOpinstname(instInfoRepository.findOne(role.getOpinstcode()).getInstname());
		}
		role = roleRepository.save(role);
		MsgIn<Role> in = new MsgIn<Role>(role);
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 打来更新页
	 * 
	 * @param rolecode
	 * @param model
	 * @return
	 */
	@RequestMapping("/roleController_openUpdatePage")
	private String openUpdatePage(@RequestParam("rolecode") String rolecode, Model model) {
		Role role = roleRepository.findOne(rolecode);
		if (role != null) {
			MsgIn<Role> in = new MsgIn<Role>(role);
			MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
			model.addAttribute("roleinfo", out.getTobj());
		}
		return "role/roleUpdate";
	}

	/**
	 * 获取选中角色菜单列表
	 */
	@RequestMapping("roleController_roleTree")
	public void prPubRoleTree(@RequestParam("rolecode") String rolecode, HttpServletResponse response) {
		List<Menu> menuList = roleRepository.findOne(rolecode).getMenus();
		MsgIn<List<Menu>> in = new MsgIn<List<Menu>>(menuList);
		writeResponse(response, in.getJsonstring());
		return;
	}

	/**
	 * 保存角色菜单权限
	 * 
	 * @param rolecode
	 * @param menus
	 */
	@RequestMapping("roleController_saveImpowerInfo")
	private void pub(@RequestParam("rolecode") String rolecode, @RequestParam("menus") String menus,
			HttpServletResponse response, HttpSession session) {
		Role role = roleRepository.findOne(rolecode);
		if(role!=null && role.getMenus()!=null){
			role.getMenus().clear();
			role = roleRepository.save(role);
		}
		String[] menucodes = menus.split(",");
		role.setMenus(menuRepository.findListByMenucodeIn(menucodes));
		role = roleRepository.save(role);
		MsgIn<Role> in = new MsgIn<Role>("0000","SUUCESS");
		MsgOut<Object> out = new MsgOut<Object>(in.getJsonstring());
		writeResponse(response, out.getJsonstring());
	}

	/**
	 * 删除角色及权限
	 */
	@RequestMapping("roleController_logoffRole")
	private void logoffRole(@RequestParam("rolecode") String rolecode, HttpServletResponse response) {
		String[] rolecodearray = rolecode.split(",");
		List<User> userList = userRepository.findAll();
		List<Role> roleList = roleRepository.findListByRolecodeIn(rolecodearray);
		MsgIn<Role> in;
		MsgOut<Object> out;
		
		try{
			if(userList!=null && userList.size()>0){
				for(User user : userList){
					for(Role role : roleList){
						if(user.getRoles()!=null && user.getRoles().size()>0 && user.getRoles().contains(role)){
							user.getRoles().remove(role);
							userRepository.save(user);
						}
					}
					
				}
			}
			roleRepository.delete(roleList);
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

	/**
	 * 角色管理获取菜单tree
	 */
	@RequestMapping("/roleController_generateTreeNode")
	private void generateTreeNode(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		if (StringUtils.isBlank(id)) {
			id = "0";
		}
		List<Menu> menuList = menuRepository.findListByPmenuinfo_menucode(id);
		List<MenuTreeDTO> treeList = new ArrayList<MenuTreeDTO>();
		for (Menu Menu : menuList) {
			String menucode = Menu.getMenucode();
			MenuTreeDTO menutree = new MenuTreeDTO();
			menutree.setId(menucode);
			menutree.setText(Menu.getMenuname());
			menutree.setMenulevel((Integer) Menu.getMenulevel());
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			jsonMap.put("parentcode", menucode);
			// 查询该菜单是否有子菜单
			List<Menu> childmenuList = menuRepository.findListByPmenuinfo_menucode(menucode);
			if (childmenuList!=null && childmenuList.size()>0) {
				menutree.setState("closed");
			} else {
				menutree.setState("open");
			}
			treeList.add(menutree);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("root", treeList);
		writeResponse(response, JSON.toJSONString(returnMap));
		return;
	}
}
