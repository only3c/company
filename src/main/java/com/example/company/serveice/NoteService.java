package com.example.company.serveice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.company.model.Note;

public interface NoteService {

	Page<Note> getNotePageByUsercode(String usercode, String notetype, Pageable pageable);

	Page<Note> getNotePageByUsercode(String usercode, String meetingname, String notes, Pageable pageable);

}
