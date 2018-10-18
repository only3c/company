package com.example.company.serveice.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.company.dto.HolidayDTO;
import com.example.company.model.Holiday;
import com.example.company.repository.HolidayRepository;
import com.example.company.serveice.HolidayService;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepository holidayRepository;

	@Override
	public Page<Holiday> getHolidayPageByUsercode(String usercode, String holidaytype, String flowtype, String cause,
			Pageable pageable) {
		return holidayRepository.findAllByUsercodeAndHolidaytypeLikeAndFlowtypeLikeAndCauseLike(usercode,
				holidaytype == null ? "%%" : "%" + holidaytype + "%", flowtype == null ? "%%" : "%" + flowtype + "%",
				cause == null ? "%%" : "%" + cause + "%", pageable);
	}

	@Override
	public Page<Holiday> getHolidayPageByInstcode(String instcode, String holidaytype, String flowtype, String cause,
			String username, Pageable pageable) {
		return holidayRepository.findAllByInstcodeAndHolidaytypeLikeAndFlowtypeLikeAndCauseLikeAndUsernameLike(instcode,
				holidaytype == null ? "%%" : "%" + holidaytype + "%", flowtype == null ? "%%" : "%" + flowtype + "%",
				cause == null ? "%%" : "%" + cause + "%", username == null ? "%%" : "%" + username + "%", pageable);
	}

	@Override
	public Holiday dtoToModel(HolidayDTO dto, Holiday model) {
		return null;
	}

	@Override
	public HolidayDTO modelToDto(Holiday model, HolidayDTO dto) {
		return null;
	}
}
