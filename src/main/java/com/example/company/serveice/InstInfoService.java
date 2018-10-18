package com.example.company.serveice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.company.model.InstInfo;

public interface InstInfoService {

	Page<InstInfo> selectInstInfoPage(String parentcode,String instcode,String instname,Pageable pageable);
}
