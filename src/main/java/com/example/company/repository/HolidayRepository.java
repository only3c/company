package com.example.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	Page<Holiday> findAllByUsercode(String usercode, Pageable pageable);

	Page<Holiday> findAllByInstcode(String instcode, Pageable pageable);

	Page<Holiday> findAllByUsercodeAndHolidaytypeLikeAndFlowtypeLikeAndCauseLike(String usercode, String holidaytype,
			String flowtype, String cause, Pageable pageable);

	Page<Holiday> findAllByInstcodeAndHolidaytypeLikeAndFlowtypeLikeAndCauseLikeAndUsernameLike(String usercode, String holidaytype,
			String flowtype, String cause,String username,Pageable pageable);
}
