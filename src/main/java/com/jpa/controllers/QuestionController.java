package com.jpa.controllers;

import com.jpa.dtos.ResponseDto;
import com.jpa.services.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing responses.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/preguntas")
public class QuestionController {

    private final IQuestionService questionService;

    /**
     * Creates a new response for a given question.
     *
     * @param idQuestion the ID of the question to which the response belongs
     * @param response the response data transfer object containing the response details
     * @return a ResponseEntity containing the created ResponseDto
     */
    @PostMapping("/{idQuestion}")
    public ResponseEntity<ResponseDto> createQuestionResponse(@PathVariable String idQuestion, @RequestBody ResponseDto response) {
        return questionService.createQuestionResponse(idQuestion, response);
    }

    /**
     * Retrieves all responses for a question.
     *
     * @param id the ID of the question to retrieve responses for
     * @return a ResponseEntity containing a list of ResponseDto objects
     */
    @GetMapping("/{id}/respuestas")
    public ResponseEntity<List<ResponseDto>> getResponsesByQuestion(@PathVariable String id) {
        return questionService.getResponsesByQuestion(id);
    }
}
