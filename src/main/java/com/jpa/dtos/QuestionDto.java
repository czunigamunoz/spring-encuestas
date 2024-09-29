package com.jpa.dtos;

import java.util.List;

/**
 * Creates a new response for a given question.
 *
 * @param idPregunta the ID of the question to which the response belongs
 * @param pregunta the response data transfer object containing the response details
 * @return a ResponseEntity containing the created ResponseDto or a not found status if the question does not exist
 */
public record QuestionDto(String idPregunta, String pregunta, List<ResponseDto> respuestas) {
    public QuestionDto() {
        this(null, null, null);
    }
}
