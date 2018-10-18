package com.example.company.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

	List<Role> findAll();
	List<Role> findAllByOrderByRolecodeDesc();
	Page<Role> findAllByRolecodeLikeAndRolenameLike(String rolecode,String rolename,Pageable pageable);
	List<Role> findListByRolecodeIn(List<String> rolecodes);
	List<Role> findListByRolecodeIn(String[] rolecodearray);
}
