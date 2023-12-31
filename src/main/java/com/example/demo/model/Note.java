package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Marks this class as a JPA entity
public class Note {
    @Id // Marks 'id' as the primary key
    @GeneratedValue(strategy = GenerationType.AUTO) // Configures the way the id is generated
    private Long id;
    private String title;
    private String content;

    // Note: for JPA entities you cannot use normal constructors i.e.
    // public Note(String title, String content){...}
    // why? because; but for 'normal' objects you can.

    // Standard getters and setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
