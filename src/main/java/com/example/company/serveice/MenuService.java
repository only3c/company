package com.example.company.serveice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.company.model.Menu;

public interface MenuService {

	List<Menu> getMenuTree(String pmenucode,String menucodes);
	
	Page<Menu> selectmenuPage(String pmenucode,String menucode,String menuname,Pageable pageable);

}
