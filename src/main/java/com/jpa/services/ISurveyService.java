package com.jpa.services;

import com.jpa.dtos.QuestionDto;
import com.jpa.dtos.SurveyDto;
import com.jpa.models.Survey;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service interface for managing surveys.
 */
public interface ISurveyService {

    /**
     * Creates a new survey.
     *
     * @param survey the survey data transfer object containing the survey details
     * @return a ResponseEntity containing the created SurveyDto or a bad request status if the survey already exists
     */
    ResponseEntity<SurveyDto> createSurvey(SurveyDto survey);

    /**
     * Retrieves all surveys.
     *
     * @return a ResponseEntity containing a list of SurveyDto objects
     */
    ResponseEntity<List<SurveyDto>> getAllSurveys();

    /**
     * Retrieves a survey by its ID.
     *
     * @param id the ID of the survey to retrieve
     * @return a ResponseEntity containing the SurveyDto or a not found status if the survey does not exist
     */
    ResponseEntity<SurveyDto> getById(String id);

    /**
     * Updates an existing survey.
     *
     * @param id the ID of the survey to update
     * @param survey the survey data transfer object containing the updated survey details
     * @return a ResponseEntity containing the updated Survey or a not found status if the survey does not exist
     */
    ResponseEntity<SurveyDto> updateSurvey(String id, SurveyDto survey);

    /**
     * Deletes a survey by its ID.
     *
     * @param id the ID of the survey to delete
     * @return a ResponseEntity with no content status or a not found status if the survey does not exist
     */
    ResponseEntity<Void> deleteSurvey(String id);

    /**
     * Retrieves all questions for a survey.
     *
     * @param id the ID of the survey to retrieve questions for
     * @return a ResponseEntity containing a list of QuestionDto objects
     */
    ResponseEntity<List<QuestionDto>> getAllQuestionsBySurveyId(String id);

    /**
     * Creates a new question for a survey.
     *
     * @param id the ID of the survey to create a question for
     * @param question the question data transfer object containing the question details
     * @return a ResponseEntity containing the created QuestionDto or a not found status if the survey does not exist
     */
    ResponseEntity<SurveyDto> createQuestionForSurvey(String id, QuestionDto question);

}