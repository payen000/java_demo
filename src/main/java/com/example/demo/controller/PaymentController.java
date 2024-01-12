package com.example.demo.controller;

import com.example.demo.dto.cardInformation;
import com.example.demo.model.CyberSourceRequestHandler;
import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import org.apache.http.client.methods.CloseableHttpResponse;
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

    CyberSourceRequestHandler apiHandler = new CyberSourceRequestHandler();

    String requestBody = apiHandler.buildPaymentRequestBody(cardInformation);
    CloseableHttpResponse response = apiHandler.sendPaymentRequest(requestBody);

    int statusCode = response.getStatusLine().getStatusCode();
    String statusReason = response.getStatusLine().getReasonPhrase();

    Note note = new Note();
    note.setTitle("Payment Attempt");
    note.setContent(statusReason);
    note.setStatusCode(statusCode);
    noteService.save(note);

    return new ModelAndView("redirect:/");
  }
}
