package com.jpa.repositories;

import com.jpa.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing Question entities from the database.
 * Extends JpaRepository to provide CRUD operations and additional query methods.
 */
public interface QuestionRepository extends JpaRepository<Question, String> {
}
