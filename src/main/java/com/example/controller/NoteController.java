package com.example.controller;

import com.example.domain.Card;
import com.example.domain.Note;
import com.example.service.CardService;
import com.example.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Louie Qin on 15/12/16.
 */
@Controller
public class NoteController {
    @Autowired
    protected CardService cardService;
    @Autowired
    protected NoteService noteService;

    @RequestMapping(value = "/create/note/{card}", method = RequestMethod.POST)
    public String createNote(Model model, @PathVariable Card card, @Valid @ModelAttribute("note") Note note, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("note", note);
            model.addAttribute("notes", card.getNotes());
            model.addAttribute("card", card);
            model.addAttribute("type", "danger");
            model.addAttribute("message", "Please provide the body for the new note.");
            return "notes";
        }

        noteService.save(note);
        card.getNotes().add(note);
        cardService.save(card);

        model.addAttribute("note", new Note());
        model.addAttribute("notes", card.getNotes());
        model.addAttribute("card", card);

        model.addAttribute("type", "success");
        model.addAttribute("message", "A new note has been created.");

        return "notes";
    }

    @RequestMapping(value = "/edit/note/{card}", method = RequestMethod.POST)
    public String editNote(Model model, @PathVariable Card card, @Valid @ModelAttribute("note") Note note, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("note", note);
            model.addAttribute("type", "danger");
            model.addAttribute("message", "The body of the note cannot be empty.");
            return "note.edit";
        }

        noteService.save(note);

        model.addAttribute("card", card);
        model.addAttribute("note", note);

        model.addAttribute("type", "success");
        model.addAttribute("message", "The note has been updated.");

        return "note.edit";
    }

    @RequestMapping(value = "/edit/note/{card}/{note}", method = RequestMethod.GET)
    public String editNoteView(Model model, @PathVariable Card card, @PathVariable Note note){
        model.addAttribute("card", card);
        model.addAttribute("note", note);
        return "note.edit";
    }

    @RequestMapping(value = "/note/{card}", method = RequestMethod.GET)
    public String note(Model model, @PathVariable Card card){
        model.addAttribute("note", new Note());
        model.addAttribute("notes", card.getNotes());
        model.addAttribute("card", card);
        return "notes";
    }

    @RequestMapping(value = "/delete/note/{card}/{note}", method = RequestMethod.GET)
    public String deleteCard(Model model, @PathVariable Card card, @PathVariable Note note){
        card.getNotes().remove(note);
        cardService.save(card);
        noteService.delete(note);

        model.addAttribute("note", new Note());
        model.addAttribute("notes", card.getNotes());
        model.addAttribute("card", card);

        model.addAttribute("type", "success");
        model.addAttribute("message", "The note has been deleted.");

        return "notes";
    }
}
