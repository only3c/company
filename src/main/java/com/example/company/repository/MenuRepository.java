package com.example.company.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.Menu;

public interface MenuRepository extends JpaRepository<Menu, String> {

	
	List<Menu> findListByPmenuinfo_menucode(String pcode);
	
	List<Menu> findListByMenucodeIn(List<String> menucodes);
	
	List<Menu> findListByMenucodeInAndPmenuinfo_menucode(List<String> menucodes,String pcode);
	
	Page<Menu> findByPmenuinfo_menucodeAndMenucodeLikeAndMenunameLike(String pmenucode,String menucode,String menuname,Pageable pageable);
	
	List<Menu> findAllByOrderByMenucodeDesc();
	
	List<Menu> findListByMenucodeIn(String[] menucodes);

	List<Menu> findAllByPmenuinfo_menucodeOrderByMenucodeDesc(String pmenucode);
}
