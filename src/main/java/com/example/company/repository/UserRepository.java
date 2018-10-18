package com.example.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.User;

//@Repository
public interface UserRepository extends JpaRepository<User,String>{
	
	User findOneByUsercode(String userCode);
	
	Page<User> findListByUsercodeAndInst_instcode(String usercode,String instcode,Pageable pageable);
	
	Page<User> findByInst_instcode(String instcode,Pageable pageable);
	
	Page<User> findByInst_instcodeAndUsercodeLikeAndUsernameLike(String instcode,String usercode,String username,Pageable pageable);
}
