package com.example.company.serveice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.company.model.Menu;
import com.example.company.repository.MenuRepository;

@Service("menuCodeService")
public class MenuCodeServiceImpl {

	@Autowired
	private MenuRepository menuRepository;
	
	/**
	 * 根据菜单级别和上级菜单编号，生成菜单编号（采取插空方式）
	 */
	public String makeMenuCode(String parentcode, String menulevel) {
		String menucode = "";
//		Long menucodeInt;
//		Integer subEndIndex = Integer.parseInt(menulevel) * 2;
//		
//		/*
//		 * 获取当前父菜单下的所有子菜单
//		 */
//		
//		List<Menu> menuList = menuRepository.findListByPmenuinfo_menucode(parentcode);
//		
//		if (menuList != null && menuList.size() > 0) {
//			String lastMenucode = "";
//			for (Menu mi : menuList) {
//				String curMenucode = mi.getMenucode();
//				
//				//判断当前两个菜单代码是否连续
//				if (!lastMenucode.equals("")) {
//					if ((Long.parseLong(lastMenucode.substring(0, subEndIndex)) + 1)
//							== Long.parseLong(curMenucode.substring(0, subEndIndex))) {
//						lastMenucode = curMenucode;
//					} else {
//						break;
//					}
//				} else {
//					lastMenucode = curMenucode;
//				}
//			}
//			menucodeInt = Long.parseLong(lastMenucode.substring(0, subEndIndex)) + 1;
//			
//		} else {
//			menucodeInt = Long.parseLong(parentcode.substring(0, subEndIndex)) + 1;
//		}
//		
//		menucode =TBLUtils.addRLetterForStr(
//				TBLUtils.addLLetterForStr(menucodeInt.toString(), Integer.parseInt(menulevel) * 2, "0"),
//				12, "0");
		List<Menu> list = menuRepository.findAllByPmenuinfo_menucodeOrderByMenucodeDesc(parentcode);
		if(list!=null &&list.size()>0){
			String code = list.get(0).getMenucode();
			menucode = String.valueOf(Integer.valueOf(code)+1);
		}else{
			menucode = parentcode+"01";
		}
		return menucode;
	}

}
