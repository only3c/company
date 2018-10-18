package com.example.company.serveice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.company.model.User;

public interface UserService {

	User selectUserByUsercode(final String usercode);
	
	Page<User> selectUserPageByUsercodeAndInstCode(String username,String usercode,String instcode,Pageable pageable);
}
