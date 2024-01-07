package com.example.demo.controller;

import com.example.demo.dto.cardInformation;
import com.example.demo.model.CyberSourceAuthorization;
import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // Marks this class as a web controller capable of handling requests
public class PaymentController {
  @Autowired // Automatically injects an instance of NoteService
  private NoteService noteService;

  @PostMapping("/payment/process")
  public ModelAndView addNote(cardInformation cardInformation) {

    CyberSourceAuthorization apiHandler = new CyberSourceAuthorization();

    String requestBody = apiHandler.buildPaymentRequestBody(cardInformation);
    int statusCode = apiHandler.sendPaymentRequest(requestBody);

    Note note = new Note();
    note.setTitle("Payment Attempt");
    note.setContent(requestBody);
    note.setStatusCode(statusCode);
    noteService.save(note);

    return new ModelAndView("redirect:/");
  }
}
