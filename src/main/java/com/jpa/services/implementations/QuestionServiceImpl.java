package com.jpa.services.implementations;

import com.jpa.dtos.ResponseDto;
import com.jpa.models.Question;
import com.jpa.models.Response;
import com.jpa.repositories.QuestionRepository;
import com.jpa.repositories.ResponseRepository;
import com.jpa.services.IQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for creating a response to a question.
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements IQuestionService {

    private final QuestionRepository questionRepository;
    private final ResponseRepository responseRepository;

    /**
     * Creates a new response for a given question.
     *
     * @param idQuestion the ID of the question to which the response belongs
     * @param response the response data transfer object containing the response details
     * @return a ResponseEntity containing the created ResponseDto or a not found status if the question does not exist
     */
    @Override
    public ResponseEntity<ResponseDto> createQuestionResponse(String idQuestion, ResponseDto response) {
        Optional<Question> question = questionRepository.findById(idQuestion);
        if (question.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Response responseOut = new Response();
        responseOut.setId(UUID.randomUUID().toString());
        responseOut.setAnswerText(response.respuesta());
         responseOut.setQuestion(question.get());

        Response responseSaved = responseRepository.save(responseOut);

        ResponseDto responseDto = new ResponseDto(
                responseSaved.getId(),
                responseSaved.getAnswerText()
        );
        return ResponseEntity.of(Optional.of(responseDto));
    }
}
