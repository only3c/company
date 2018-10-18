package com.example.company.serveice.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.company.model.Note;
import com.example.company.repository.NoteRepository;
import com.example.company.serveice.NoteService;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;
	@Override
	public Page<Note> getNotePageByUsercode(String usercode, String notetype, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Note> getNotePageByUsercode(String usercode, String meetingname, String notes, Pageable pageable) {
		// TODO Auto-generated method stub
		return noteRepository.findAllByUsercodeAndMeetingnameLikeAndNotesLike(usercode, meetingname==null?"%%":"%"+meetingname+"%", notes==null?"%%":"%"+notes+"%", pageable);
	}

}
