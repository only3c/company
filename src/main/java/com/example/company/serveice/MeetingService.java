package com.example.company.serveice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.company.model.Meeting;

public interface MeetingService {

	public Page<Meeting> getMeetingPageByInstcode(String instcode, String meetingtype, String flowtype, String cause,
			String username, Pageable pageable);

	public Page<Meeting> getMeetingPageByUsercode(String usercode, String meetingtype, String flowtype, String cause,
			Pageable pageable);

}
