package com.jpa.repositories;

import com.jpa.models.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Survey entities from the database.
 * Extends JpaRepository to provide CRUD operations and additional query methods.
 */
public interface SurveyRepository extends JpaRepository<Survey, String> {
    // Encuentra encuestas por su titulo
    Optional<Survey> findByTitle(String title);
}
