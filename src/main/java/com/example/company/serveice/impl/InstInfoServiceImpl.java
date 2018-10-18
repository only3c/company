package com.example.company.serveice.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.company.model.InstInfo;
import com.example.company.repository.InstInfoRepository;
import com.example.company.serveice.InstInfoService;

@Service
@Transactional
public class InstInfoServiceImpl implements InstInfoService {

	@Autowired
	private InstInfoRepository infoRepository;

	@Override
	public Page<InstInfo> selectInstInfoPage(String parentcode, String instcode, String instname, Pageable pageable) {
		Page<InstInfo> page = infoRepository.findByPinstinfo_instcodeAndInstcodeLikeAndInstnameLike(parentcode,instcode == null ? "%%" : "%"+instcode+"%", instname == null ? "%%" : "%"+instname+"%", pageable);
		return page;
	}
}
