package com.example.company.serveice.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.company.model.Menu;
import com.example.company.repository.MenuRepository;
import com.example.company.serveice.MenuService;
import com.example.company.util.StringUtils;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> getMenuTree(String pmenucode, String menucodes) {
		List<Menu> menuList= new ArrayList<>();
		if(pmenucode!=null &&!"".equals(pmenucode) && menucodes!=null && !"".equals(menucodes)){
			List<String> rolecodeList = null;
			//判断权限
			if(menucodes!=null){
				rolecodeList = new ArrayList<String>();
				for (String menucode : menucodes.split(",")) {
					if (StringUtils.isNotBlank(menucode)) {
						rolecodeList.add(menucode);
					}
				}
			}
			menuList = menuRepository.findListByMenucodeInAndPmenuinfo_menucode(rolecodeList, pmenucode);
		}else if(pmenucode!=null && !"".equals(pmenucode)&& (menucodes==null || "".equals(menucodes))){
			menuList = menuRepository.findListByPmenuinfo_menucode(pmenucode);
		}else if((pmenucode==null || "".equals(pmenucode)) && (menucodes!=null || !"".equals(menucodes))){
			List<String> rolecodeList = null;
			//判断权限
			if(menucodes!=null){
				rolecodeList = new ArrayList<String>();
				for (String menucode : menucodes.split(",")) {
					if (StringUtils.isNotBlank(menucode)) {
						rolecodeList.add(menucode);
					}
				}
			}
			menuList = menuRepository.findListByMenucodeIn(rolecodeList);
		}else{
			menuList = menuRepository.findAll();
		}
		return menuList;
	}

	@Override
	public Page<Menu> selectmenuPage(String pmenucode, String menucode, String menuname, Pageable pageable) {
		Page<Menu> page = menuRepository.findByPmenuinfo_menucodeAndMenucodeLikeAndMenunameLike(pmenucode,menucode == null ? "%%" : "%"+menucode+"%", menuname == null ? "%%" : "%"+menuname+"%", pageable);
		return page;
	}

}
