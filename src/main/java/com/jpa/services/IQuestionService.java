package com.jpa.services;

import com.jpa.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service interface for managing responses.
 */
public interface IQuestionService {
    /**
     * Creates a new response for a given question.
     *
     * @param idQuestion the ID of the question to which the response belongs
     * @param response the response data transfer object containing the response details
     * @return a ResponseEntity containing the created ResponseDto or a not found status if the question does not exist
     */
    ResponseEntity<ResponseDto> createQuestionResponse(String idQuestion, ResponseDto response);

    /**
     * Retrieves all responses for a question.
     *
     * @param id the ID of the question to retrieve responses for
     * @return a ResponseEntity containing a list of ResponseDto objects
     */
    ResponseEntity<List<ResponseDto>> getResponsesByQuestion(String id);
}
