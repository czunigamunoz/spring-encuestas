package com.jpa.repositories;

import com.jpa.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing Question entities from the database.
 * Extends JpaRepository to provide CRUD operations and additional query methods.
 */
public interface QuestionRepository extends JpaRepository<Question, String> {

    /**
     * Finds all questions associated with a given survey ID.
     *
     * @param surveyId the ID of the survey
     * @return a list of questions associated with the survey
     */
    List<Question> findBySurveyId(String surveyId);
}
