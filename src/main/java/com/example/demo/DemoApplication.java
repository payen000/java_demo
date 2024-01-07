package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Marks this class as a configuration class and enables auto-configuration
public class DemoApplication {

  public static void main(String[] args) {
    // Entry point to start the Spring Boot application
    SpringApplication.run(DemoApplication.class, args);
  }
}
