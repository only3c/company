package com.example.company.serveice.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.company.model.Meeting;
import com.example.company.repository.MeetingRepository;
import com.example.company.serveice.MeetingService;

@Service
@Transactional
public class MeetingServiceImpl implements MeetingService{

	@Autowired
	private MeetingRepository meetingRepository;
	@Override
	public Page<Meeting> getMeetingPageByInstcode(String instcode, String meetingtype, String flowtype, String summary,
			String username, Pageable pageable) {
		return meetingRepository.findAllByInstcodeAndMeetingtypeLikeAndFlowtypeLikeAndSummaryLikeAndUsernameLike(instcode, meetingtype==null?"%%":"%"+meetingtype+"%", flowtype==null?"%%":"%"+flowtype+"%", summary==null?"%%":"%"+summary+"%", username==null?"%%":"%"+username+"%", pageable);
	}

	@Override
	public Page<Meeting> getMeetingPageByUsercode(String usercode, String meetingtype, String flowtype, String cause,
			Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
