package com.example.demo.model;

import com.example.demo.dto.cardInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class CyberSourceAuthorization {
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
