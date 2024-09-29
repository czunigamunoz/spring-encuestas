package com.jpa.controllers;

import com.jpa.dtos.QuestionDto;
import com.jpa.dtos.SurveyDto;
import com.jpa.services.ISurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing surveys.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/encuestas")
public class SurveyController {

    private final ISurveyService surveyService;

    /**
     * Retrieves all surveys.
     *
     * @return a ResponseEntity containing a list of SurveyDto objects
     */
    @GetMapping()
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    /**
     * Retrieves a survey by its ID.
     *
     * @param id the ID of the survey to retrieve
     * @return a ResponseEntity containing the SurveyDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<SurveyDto> getSurveyById(@PathVariable String id) {
        return surveyService.getById(id);
    }

    /**
     * Creates a new survey.
     *
     * @param survey the survey data transfer object containing the survey details
     * @return a ResponseEntity containing the created SurveyDto
     */
    @PostMapping()
    public ResponseEntity<SurveyDto> createSurvey(@RequestBody SurveyDto survey) {
        return surveyService.createSurvey(survey);
    }

    /**
     * Updates an existing survey.
     *
     * @param id the ID of the survey to update
     * @param survey the survey data transfer object containing the updated survey details
     * @return a ResponseEntity containing the updated Survey
     */
    @PutMapping("/{id}")
    public ResponseEntity<SurveyDto> updateSurvey(@PathVariable String id, @RequestBody SurveyDto survey) {
        return surveyService.updateSurvey(id, survey);
    }

    /**
     * Deletes a survey by its ID.
     *
     * @param id the ID of the survey to delete
     * @return a ResponseEntity with no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable String id) {
        return surveyService.deleteSurvey(id);
    }

    // Add a new endpoint to retrieve all questions for a survey
    /**
     * Retrieves all questions for a survey.
     *
     * @param id the ID of the survey to retrieve questions for
     * @return a ResponseEntity containing a list of QuestionDto objects
     */
    @GetMapping("/{id}/preguntas")
    public ResponseEntity<List<QuestionDto>> getAllQuestionsBySurveyId(@PathVariable String id) {
        return surveyService.getAllQuestionsBySurveyId(id);
    }

    // Add a new endpoint to create a new question for a survey
    /**
     * Creates a new question for a survey.
     *
     * @param id the ID of the survey to create a question for
     * @param question the question data transfer object containing the question details
     * @return a ResponseEntity containing the created QuestionDto
     */
    @PostMapping("/{id}/preguntas")
    public ResponseEntity<SurveyDto> createQuestion(@PathVariable String id, @RequestBody QuestionDto question) {
        return surveyService.createQuestionForSurvey(id, question);
    }
}