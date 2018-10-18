package com.example.company.serveice.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.example.company.model.InstInfo;
import com.example.company.repository.InstInfoRepository;
import com.example.company.util.StringUtils;

@Service("instCodeService")
public class InstCodeServiceImpl{

	@Resource
	private InstInfoRepository instInfoRepository;
	
	
	public String makeCode(String province, String city, String instclass) {
		String instcode = "";
		List<InstInfo> InstInfoList = instInfoRepository.findAllByProvinceAndCityAndInstclassOrderByInstcodeDesc(province,city,instclass);
		InstInfo instinfo = (InstInfoList!=null&&InstInfoList.size()>0? InstInfoList.get(0):null);
		
		if (instinfo != null) {
			Integer seqInt = (Integer.valueOf(instinfo.getInstcode().substring(5, 8)) + 1);
			String seqStr = seqInt.toString();
			instcode = province + city + instclass + StringUtils.addLLetterForStr(seqStr, 3, "0");
		} else {
			instcode = province + city + instclass + StringUtils.addLLetterForStr("1", 3, "0");
		}
				
		return instcode;
	}

}
