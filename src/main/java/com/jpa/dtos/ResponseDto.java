package com.jpa.dtos;

/**
 * Creates a new response for a given question.
 *
 * @param idRespuesta the ID of the response
 * @param respuesta the response data transfer object containing the response details
 * @return a ResponseEntity containing the created ResponseDto or a not found status if the response does not exist
 */
public record ResponseDto(String idRespuesta, String respuesta) {
    public ResponseDto() {
        this(null, null);
    }
}
