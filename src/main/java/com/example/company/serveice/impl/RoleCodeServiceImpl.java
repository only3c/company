package com.example.company.serveice.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.company.model.Role;
import com.example.company.repository.RoleRepository;

@Service("roleCodeService")
public class RoleCodeServiceImpl {

	@Autowired
	private RoleRepository roleRepository;
	
	public String makeRoleCode() {
		String menucode = "0";
		List<Role> list = roleRepository.findAllByOrderByRolecodeDesc();
		if(list!=null &&list.size()>0){
			menucode = String.valueOf(Integer.valueOf(list.get(0).getRolecode())+1);
		}
		return menucode;
	}

}
