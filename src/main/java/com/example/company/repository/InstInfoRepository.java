package com.example.company.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.InstInfo;

public interface InstInfoRepository extends JpaRepository<InstInfo, String> {

	List<InstInfo> findAll();
	List<InstInfo> findListByPinstinfo_instcodeIsNull();
	List<InstInfo> findListByInstcodeAndPinstinfo_instcode(String instcode, String pinstinfo_instcode);
	List<InstInfo> findListByInstcode(String instcode);
	InstInfo findOneByInstcode(String instcode);
	List<InstInfo> findListByPinstinfo_instcode(String pinstinfo_instcode);
	Page<InstInfo>findByPinstinfo_instcodeAndInstcodeLikeAndInstnameLike(String parentcode,String instcode ,String instname,Pageable pageable);
	List<InstInfo> findAllByProvinceAndCityAndInstclassOrderByInstcodeDesc(String province,String city,String instclass);
}
