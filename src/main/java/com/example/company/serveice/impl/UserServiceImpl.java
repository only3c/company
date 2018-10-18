package com.example.company.serveice.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.company.model.User;
import com.example.company.repository.UserRepository;
import com.example.company.serveice.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User selectUserByUsercode(String usercode) {
		User user = userRepository.findOneByUsercode(usercode);
		return user;
	}

	@Override
	public Page<User> selectUserPageByUsercodeAndInstCode(String username,String usercode, String instcode, Pageable pageable) {
		Page<User> page = userRepository.findByInst_instcodeAndUsercodeLikeAndUsernameLike(instcode,usercode == null ? "%%" : "%"+usercode+"%", username == null ? "%%" : "%"+username+"%", pageable);
//		Page<User> page = userRepository.findByInst_instcode(instcode, pageable);
		return page ;
	}

}
