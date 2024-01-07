package com.example.demo.controller;

import com.example.demo.dto.cardInformation;
import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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

    String requestBody = this.buildPaymentRequestBody(cardInformation);
    int statusCode = this.sendPaymentRequest(requestBody);

    Note note = new Note();
    note.setTitle("Payment Attempt");
    note.setContent(requestBody);
    note.setStatusCode(statusCode);
    noteService.save(note);

    return new ModelAndView("redirect:/");
  }

  public String buildPaymentRequestBody(cardInformation cardInformation) {
    ObjectMapper mapper = new ObjectMapper();
    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(cardInformation);
    } catch (JsonProcessingException e) {
      e.printStackTrace(); // or handle the exception in a more user-friendly manner
      jsonString = "{}"; // or handle this situation however is appropriate
    }
    return jsonString;
  }

  public int sendPaymentRequest(String requestBody) {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create("https://apitest.cybersource.com/pts/v2/payments"))
            .header("Content-Type", "application/json")
            .header("v-c-merchant-id", "testrest")
            .POST(BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response;
    int statusCode;
    try {
      response = client.send(request, BodyHandlers.ofString());
      statusCode = response.statusCode();
      System.out.println(statusCode);
      System.out.println(response.body());
      return statusCode;
    } catch (IOException | InterruptedException e) {
      e.printStackTrace(); // or handle the exception in a more user-friendly manner
      return 400; // System error
    }
  }
}
