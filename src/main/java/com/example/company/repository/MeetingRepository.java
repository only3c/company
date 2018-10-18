package com.example.company.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting,Long>{

	Page<Meeting> findAllByInstcodeAndMeetingtypeLikeAndFlowtypeLikeAndSummaryLikeAndUsernameLike(String instcode, String meetingtype,
			String flowtype, String summary,String username,Pageable pageable);
	
	List<Meeting> findAllByInstcodeAndMeetingtype(String instcode,String meetingtype);
}
