package com.jpa.controllers;

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
}