package com.example.demo.dto;

public class NoteDTO {
  private String title;
  private String content;

  // Getters
  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  // Setters
  // NOTE: for NoteDTO objects defined by input form (see index.html), setter methods are required.
  // otherwise this.title, this.content, etc. will return 'null'.
  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
