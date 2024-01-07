package com.example.demo.controller;

import com.example.demo.dto.NoteDTO;
import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // Marks this class as a web controller capable of handling requests
public class NoteController {
  @Autowired // Automatically injects an instance of NoteService
  private NoteService noteService;

  @PostMapping("/addNote")
  public ModelAndView addNote(NoteDTO noteDTO) {
    Note note = new Note();
    String noteTitle = noteDTO.getTitle();
    String noteContent = noteDTO.getContent();
    note.setTitle(noteTitle);
    note.setContent(noteContent);
    noteService.save(note);
    return new ModelAndView("redirect:/");
  }

  @GetMapping("/") // Maps the root URL to this method
  public String viewHomePage(Model model) {
    // Adds a list of all notes to the model to be displayed
    model.addAttribute("notes", noteService.findAll());
    return "index"; // Returns the name of the Thymeleaf template to render
  }

  @GetMapping("/note/{id}") // Maps URLs like /note/1 to this method
  public String viewNote(@PathVariable Long id, Model model) {
    // Adds a single note to the model to be displayed
    Note note = noteService.findById(id);
    model.addAttribute("note", note);
    return "note"; // Returns the name of the Thymeleaf template to render
  }

  // Additional methods for create, update, and delete operations can be added here
}
