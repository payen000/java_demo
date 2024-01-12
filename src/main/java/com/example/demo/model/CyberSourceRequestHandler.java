package com.example.demo.model;

import com.example.demo.dto.cardInformation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CyberSourceRequestHandler {
  public String buildPaymentRequestBody(cardInformation cardInformation) {
    ObjectMapper mapper = new ObjectMapper();

    // Client Reference Information
    ObjectNode clientReferenceInfo = mapper.createObjectNode();
    clientReferenceInfo.put("code", "TC50171_3");

    // Payment Information
    ObjectNode cardInfo = mapper.createObjectNode();
    cardInfo.put("number", Long.toString(cardInformation.getCardNumber()));
    cardInfo.put("expirationMonth", Integer.toString(cardInformation.getCardExpirationMonth()));
    cardInfo.put("expirationYear", Integer.toString(cardInformation.getCardExpirationYear()));

    ObjectNode paymentInfo = mapper.createObjectNode();
    paymentInfo.set("card", cardInfo);

    // Order Information - Amount Details
    ObjectNode amountDetails = mapper.createObjectNode();
    amountDetails.put("totalAmount", "1.0");
    amountDetails.put("currency", "USD");

    // Order Information - Bill To
    ObjectNode billTo = mapper.createObjectNode();
    billTo.put("firstName", cardInformation.getCustomerFirstName());
    billTo.put("lastName", cardInformation.getCustomerLastName());
    billTo.put("postalCode", Integer.toString(cardInformation.getCustomerZip()));
    billTo.put("country", cardInformation.getCustomerCountry());
    billTo.put("email", "test@gmail.com");
    billTo.put("phoneNumber", "1111111111");

    ObjectNode orderInfo = mapper.createObjectNode();
    orderInfo.set("amountDetails", amountDetails);
    orderInfo.set("billTo", billTo);

    // Assembling the final JSON object
    ObjectNode paymentInformation = mapper.createObjectNode();
    paymentInformation.set("clientReferenceInformation", clientReferenceInfo);
    paymentInformation.set("paymentInformation", paymentInfo);
    paymentInformation.set("orderInformation", orderInfo);

    String jsonString;
    try {
      jsonString = mapper.writeValueAsString(paymentInformation);
    } catch (JsonProcessingException e) {
      e.printStackTrace(); // or handle the exception in a more user-friendly manner
      jsonString = "{}"; // or handle this situation however is appropriate
    }
    return jsonString;
  }

  /**
   * Method to build headers using test credentials (in practice, they should be obtained from a
   * config file or as private attributes of a class.)
   *
   * @param requestBody
   * @param merchantId
   * @param digest
   * @param date
   * @return
   */
  public String buildHeaderSignature(
      String requestBody, String merchantId, String digest, String date) {
    try {
      String signingString =
          "host: apitest.cybersource.com\n"
              + "date: "
              + date
              + "\n"
              + "(request-target): post /pts/v2/payments\n"
              + "digest: "
              + digest
              + "\n"
              + "v-c-merchant-id: "
              + merchantId;
      String secretKey = "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=";
      SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.decodeBase64(secretKey), "HmacSHA256");
      Mac sha256Hmac = Mac.getInstance("HmacSHA256");
      sha256Hmac.init(secretKeySpec);
      String signature =
          Base64.encodeBase64String(
              sha256Hmac.doFinal(signingString.getBytes(StandardCharsets.UTF_8)));
      String keyId = "08c94330-f618-42a3-b09d-e1e43be5efda";
      String signatureHeaders = "host date (request-target) digest v-c-merchant-id";
      // Generate signature
      return String.format(
          "keyid=\"%s\", algorithm=\"HmacSHA256\", headers=\"%s\", signature=\"%s\"",
          keyId, signatureHeaders, signature);
    } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
      e.printStackTrace();
    }
    return "";
  }

  public CloseableHttpResponse sendPaymentRequest(String requestBody) {
    String url = "https://apitest.cybersource.com/pts/v2/payments";

    // Calculate Digest
    String digest = "SHA-256=" + Base64.encodeBase64String(DigestUtils.sha256(requestBody));

    // Generate date
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    String date = dateFormat.format(new Date());

    // Prepare signing string
    String merchantId = "testrest";
    String headerSignature = buildHeaderSignature(requestBody, merchantId, digest, date);

    // Execute request
    CloseableHttpResponse response = null;
    try {
      CloseableHttpClient client = HttpClients.createDefault();

      // Create the HTTP client and request
      HttpPost httpPost = new HttpPost(url);
      httpPost.setHeader("content-type", "application/json");
      httpPost.setHeader("Digest", digest);
      httpPost.setHeader("Signature", headerSignature);
      httpPost.setHeader("v-c-merchant-id", merchantId);
      httpPost.setHeader("Date", date);
      httpPost.setEntity(new StringEntity(requestBody));
      response = client.execute(httpPost);
      return response;
    } catch (IllegalStateException | IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    ;
    return response;
  }
}
