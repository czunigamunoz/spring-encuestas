package com.jpa.controllers;

import com.jpa.dtos.ResponseDto;
import com.jpa.services.IQuestionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the ResponseController class.
 */
@SpringBootTest
class QuestionControllerTest {

    @Mock
    private IQuestionService responseService;

    @InjectMocks
    private QuestionController questionController;

    /**
     * Test for successfully creating a response.
     */
    @Test
    void createQuestionResponseSuccessfully() {
        String questionId = UUID.randomUUID().toString();
        ResponseDto responseDto = new ResponseDto(UUID.randomUUID().toString(), "Sample Answer");

        when(responseService.createQuestionResponse(any(String.class), any(ResponseDto.class)))
                .thenReturn(new ResponseEntity<>(responseDto, HttpStatus.CREATED));

        ResponseEntity<ResponseDto> response = questionController.createQuestionResponse(questionId, responseDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    /**
     * Test for handling the case when the question is not found.
     */
    @Test
    void createQuestionResponseNotFound() {
        String questionId = UUID.randomUUID().toString();
        ResponseDto responseDto = new ResponseDto(UUID.randomUUID().toString(), "Sample Answer");

        when(responseService.createQuestionResponse(any(String.class), any(ResponseDto.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        ResponseEntity<ResponseDto> response = questionController.createQuestionResponse(questionId, responseDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}