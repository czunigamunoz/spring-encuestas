package com.jpa.dtos;

import java.util.List;

/**
 * Creates a new response for a given question.
 *
 * @param idEncuesta the ID of the survey
 * @param titulo the title of the survey
 * @param preguntas the list of questions in the survey
 * @return a ResponseEntity containing the created SurveyDto or a not found status if the survey does not exist
 */
public record SurveyDto(String idEncuesta, String titulo, List<QuestionDto> preguntas) {
}
