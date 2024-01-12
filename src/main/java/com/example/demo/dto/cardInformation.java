package com.example.demo.dto;

public class cardInformation {
  private String customerStreet;
  private int customerZip;
  private Long cardNumber;
  private int cardExpirationMonth;
  private int cardExpirationYear;
  private int cardCode;
  private String customerFirstName;
  private String customerLastName;
  private String customerCountry;

  public String getCustomerLastName() {
    return customerLastName;
  }

  public void setCustomerLastName(String customerLastName) {
    this.customerLastName = customerLastName;
  }

  public String getCustomerCountry() {
    return customerCountry;
  }

  public void setCustomerCountry(String customerCountry) {
    this.customerCountry = customerCountry;
  }

  public String getCustomerFirstName() {
    return customerFirstName;
  }

  public void setCustomerFirstName(String customerFirstName) {
    this.customerFirstName = customerFirstName;
  }

  public String getCustomerStreet() {
    return customerStreet;
  }

  public void setCustomerStreet(String customerStreet) {
    this.customerStreet = customerStreet;
  }

  public int getCustomerZip() {
    return customerZip;
  }

  public void setCustomerZip(int customerZip) {
    this.customerZip = customerZip;
  }

  public Long getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Long cardNumber) {
    this.cardNumber = cardNumber;
  }

  public int getCardExpirationMonth() {
    return cardExpirationMonth;
  }

  public void setCardExpirationMonth(int cardExpirationMonth) {
    this.cardExpirationMonth = cardExpirationMonth;
  }

  public int getCardExpirationYear() {
    return cardExpirationYear;
  }

  public void setCardExpirationYear(int cardExpirationYear) {
    this.cardExpirationYear = cardExpirationYear;
  }

  public int getCardCode() {
    return cardCode;
  }

  public void setCardCode(int cardCode) {
    this.cardCode = cardCode;
  }
}
