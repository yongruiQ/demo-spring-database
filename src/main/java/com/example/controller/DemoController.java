package com.example.controller;

import com.example.domain.Card;
import com.example.domain.CardRepository;
import com.example.domain.Note;
import com.example.service.CardService;
import com.example.service.NoteService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Louie Qin on 24/11/16.
 */
@Controller
public class DemoController {
    @Autowired
    protected CardService cardService;
    @Autowired
    protected NoteService noteService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Card json(){
        Card card = new Card();
        card.setTitle("JSON example");
        return card;
    }

    // ------------------------- Card Routes ------------------------------------------//
    @RequestMapping(value = "/create/card", method = RequestMethod.POST)
    public String createCard(Model model, @Valid @ModelAttribute("card") Card card, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("card", card);
            model.addAttribute("cards", cardService.findAll());
            model.addAttribute("type", "danger");
            model.addAttribute("message", "Please provide a title for the new card.");
            return "cards";
        }

        cardService.save(card);

        model.addAttribute("card", new Card());
        model.addAttribute("cards", cardService.findAll());

        model.addAttribute("type", "success");
        model.addAttribute("message", "A new card has been created.");

        return "cards";
    }

    @RequestMapping(value = "/delete/card/{card}", method = RequestMethod.GET)
    public String deleteCard(Model model, @PathVariable Card card){
        cardService.delete(card);

        model.addAttribute("card", new Card());
        model.addAttribute("cards", cardService.findAll());

        model.addAttribute("type", "success");
        model.addAttribute("message", "The card has been deleted.");

        return "cards";
    }

    @RequestMapping(value = "/card", method = RequestMethod.GET)
    public String card(Model model){
        model.addAttribute("card", new Card());
        model.addAttribute("cards", cardService.findAll());
        return "cards";
    }

    // ------------------------- Note Routes ------------------------------------------//
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
