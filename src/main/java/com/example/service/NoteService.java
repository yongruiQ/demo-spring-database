package com.example.service;

import com.example.domain.Note;
import com.example.domain.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Louie Qin on 01/12/16.
 */
@Service
public class NoteService {
    @Autowired
    protected  NoteRepository noteRepository;

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void delete(Note note) { noteRepository.delete(note); }
}
