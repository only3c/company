package com.example.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.company.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

	//Page<Note> getNotePageByUsercode(String usercode, String meetingname, String notes, Pageable pageable);
	Page<Note> findAllByUsercodeAndMeetingnameLikeAndNotesLike(String usercode,String meetingname, String notes,Pageable pageable);

}
