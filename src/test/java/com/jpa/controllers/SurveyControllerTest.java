package com.jpa.controllers;

import com.jpa.dtos.QuestionDto;
import com.jpa.dtos.ResponseDto;
import com.jpa.dtos.SurveyDto;
import com.jpa.services.ISurveyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the SurveyController class.
 */
@SpringBootTest
class SurveyControllerTest {

    @Mock
    private ISurveyService surveyService;

    @InjectMocks
    private SurveyController surveyController;

    /**
     * Test for successfully retrieving all surveys.
     */
    @Test
    void getAllSurveysSuccessfully() {
        List<QuestionDto> questions = List.of(new QuestionDto(UUID.randomUUID().toString(), "Question 1", new ArrayList<>()));
        SurveyDto survey = new SurveyDto(UUID.randomUUID().toString(), "Survey 1", questions);

        // Mock the createSurvey method to return a valid ResponseEntity
        when(surveyService.createSurvey(any(SurveyDto.class))).thenReturn(new ResponseEntity<>(survey, HttpStatus.CREATED));

        SurveyDto surveyCreated = surveyController.createSurvey(survey).getBody();
        assert surveyCreated != null;
        when(surveyService.getAllSurveys()).thenReturn(new ResponseEntity<>(List.of(surveyCreated), HttpStatus.OK));

        ResponseEntity<List<SurveyDto>> response = surveyController.getAllSurveys();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(surveyCreated), response.getBody());
    }

    /**
     * Test for retrieving all surveys when the list is empty.
     */
    @Test
    void getAllSurveysEmptyList() {
        when(surveyService.getAllSurveys()).thenReturn(new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK));

        ResponseEntity<List<SurveyDto>> response = surveyController.getAllSurveys();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    /**
     * Test for successfully creating a survey.
     */
    @Test
    void createSurveySuccessfully() {
        List<ResponseDto> responses = List.of(new ResponseDto(UUID.randomUUID().toString(), "Response 1"));
        List<QuestionDto> questions = List.of(new QuestionDto(UUID.randomUUID().toString(), "Question 1", responses));
        SurveyDto surveyDto = new SurveyDto(UUID.randomUUID().toString(), "New Survey", questions);
        when(surveyService.createSurvey(any(SurveyDto.class))).thenReturn(new ResponseEntity<>(surveyDto, HttpStatus.CREATED));

        ResponseEntity<SurveyDto> response = surveyController.createSurvey(surveyDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(surveyDto, response.getBody());
    }

    /**
     * Test for successfully updating a survey.
     */
    @Test
    void updateSurveySuccessfully() {
        List<ResponseDto> responses = List.of(new ResponseDto(UUID.randomUUID().toString(), "Response 1"));
        List<QuestionDto> questions = List.of(new QuestionDto(UUID.randomUUID().toString(), "Question 1", responses));
        SurveyDto surveyDto = new SurveyDto(UUID.randomUUID().toString(), "New Survey", questions);

        SurveyDto updatedSurvey = new SurveyDto(
                surveyDto.idEncuesta(),
                "Updated Survey",
                questions
        );

        when(surveyService.updateSurvey(any(String.class), any(SurveyDto.class))).thenReturn(new ResponseEntity<>(updatedSurvey, HttpStatus.OK));

        ResponseEntity<SurveyDto> response = surveyController.updateSurvey(surveyDto.idEncuesta(), updatedSurvey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSurvey, response.getBody());
    }

    /**
     * Test for successfully deleting a survey.
     */
    @Test
    void deleteSurveySuccessfully() {
        String surveyId = UUID.randomUUID().toString();
        when(surveyService.deleteSurvey(surveyId)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        ResponseEntity<Void> response = surveyController.deleteSurvey(surveyId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}