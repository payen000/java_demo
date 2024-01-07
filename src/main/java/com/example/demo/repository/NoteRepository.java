package com.example.demo.repository;

import com.example.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this interface as a repository component
public interface NoteRepository extends JpaRepository<Note, Long> {
  // Inherits CRUD operations and query methods from JpaRepository
}
