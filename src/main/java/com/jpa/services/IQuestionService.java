package com.jpa.services;

import com.jpa.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;

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
}
