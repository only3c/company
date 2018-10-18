package com.example.company.serveice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.company.dto.HolidayDTO;
import com.example.company.model.Holiday;

public interface HolidayService {

	Holiday dtoToModel(HolidayDTO dto, Holiday model);

	HolidayDTO modelToDto(Holiday model, HolidayDTO dto);

	Page<Holiday> getHolidayPageByUsercode(String usercode, String holidaytype, String flowtype, String cause,
			Pageable pageable);

	Page<Holiday> getHolidayPageByInstcode(String instcode, String holidaytype, String flowtype, String cause,
			String username, Pageable pageable);

}
