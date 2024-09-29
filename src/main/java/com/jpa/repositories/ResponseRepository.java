package com.jpa.repositories;

import com.jpa.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing Response entities from the database.
 * Extends JpaRepository to provide CRUD operations and additional query methods.
 */
public interface ResponseRepository extends JpaRepository<Response, String> {

    List<Response> findByQuestionId(String questionId);
}
