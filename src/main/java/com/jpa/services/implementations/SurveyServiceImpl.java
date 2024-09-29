package com.jpa.services.implementations;

import com.jpa.dtos.QuestionDto;
import com.jpa.dtos.ResponseDto;
import com.jpa.dtos.SurveyDto;
import com.jpa.models.Question;
import com.jpa.models.Response;
import com.jpa.models.Survey;
import com.jpa.repositories.QuestionRepository;
import com.jpa.repositories.SurveyRepository;
import com.jpa.services.ISurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for creating, reading, updating, and deleting surveys.
 */
@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements ISurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    /**
     * Creates a new survey.
     *
     * @param survey the survey data transfer object containing the survey details
     * @return a ResponseEntity containing the created SurveyDto or a bad request status if the survey already exists
     */
    @Override
    public ResponseEntity<SurveyDto> createSurvey(SurveyDto survey) {
        Optional<Survey> existSurvey = surveyRepository.findByTitle(survey.titulo());
        if (existSurvey.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        // new survey
        Survey surveyIn = new Survey();
        surveyIn.setId(UUID.randomUUID().toString());
        surveyIn.setTitle(survey.titulo());

        if (survey.preguntas() == null) {
            surveyIn.setQuestions(new ArrayList<>());
        }else {
            List<Question> questions = survey.preguntas().stream()
                    .map(question -> {
                        Question questionToSave = new Question();
                        questionToSave.setId(UUID.randomUUID().toString());
                        questionToSave.setSurvey(surveyIn);
                        questionToSave.setQuestionText(question.pregunta());
                        questionToSave.setResponses(new ArrayList<>());
                        return questionToSave;
                    }).toList();

            surveyIn.setQuestions(questions);
            Survey surveySaved = surveyRepository.save(surveyIn);

            List<QuestionDto> questionsDto = questions.stream()
                    .map(question -> new QuestionDto(
                            question.getId(),
                            question.getQuestionText(),
                            new ArrayList<>()))
                    .toList();
        }

        Survey surveySaved = surveyRepository.save(surveyIn);
        SurveyDto surveyDto = new SurveyDto(
                surveySaved.getId(),
                surveySaved.getTitle(),
                surveySaved.getQuestions().stream()
                        .map(question -> new QuestionDto(
                                question.getId(),
                                question.getQuestionText(),
                                question.getResponses()
                                        .stream()
                                        .map(response -> new ResponseDto(
                                                response.getId(),
                                                response.getAnswerText()
                                        )).collect(java.util.stream.Collectors.toList())
                        )).toList()
        );

        return ResponseEntity.ok(surveyDto);
    }

    /**
     * Retrieves all surveys.
     *
     * @return a ResponseEntity containing a list of SurveyDto objects
     */
    @Override
    public ResponseEntity<List<SurveyDto>> getAllSurveys() {
        List<SurveyDto> surveysDto = surveyRepository.findAll().stream()
                .map(survey -> {
                    List<QuestionDto> questionsDto = survey
                            .getQuestions()
                            .stream()
                            .map(question -> new QuestionDto(
                                    question.getId(),
                                    question.getQuestionText(),
                                    question.getResponses()
                                            .stream()
                                            .map(response -> new ResponseDto(
                                                    response.getId(),
                                                    response.getAnswerText()
                                            )).collect(java.util.stream.Collectors.toList())
                            )).toList();
                    return new SurveyDto(
                            survey.getId(),
                            survey.getTitle(),
                            questionsDto);
                }).toList();
        return ResponseEntity.ok(surveysDto);
    }

    /**
     * Retrieves a survey by its ID.
     *
     * @param id the ID of the survey to retrieve
     * @return a ResponseEntity containing the SurveyDto or a not found status if the survey does not exist
     */
    @Override
    public  ResponseEntity<SurveyDto> getById(String id) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Survey surveyFound = survey.get();
        List<QuestionDto> questionsDto = surveyFound.getQuestions().stream()
                .map(question -> new QuestionDto(
                        question.getId(),
                        question.getQuestionText(),
                        question.getResponses()
                                .stream()
                                .map(response -> new ResponseDto(
                                        response.getId(),
                                        response.getAnswerText()
                                )).collect(java.util.stream.Collectors.toList())
                )).toList();
        SurveyDto surveyDto = new SurveyDto(
                surveyFound.getId(),
                surveyFound.getTitle(),
                questionsDto
        );
        return ResponseEntity.ok(surveyDto);
    }

    @Override
    public ResponseEntity<List<QuestionDto>> getAllQuestionsBySurveyId(String id) {
        List<QuestionDto> questions = questionRepository.findBySurveyId(id).stream()
                .map(question -> new QuestionDto(
                        question.getId(),
                        question.getQuestionText(),
                        question.getResponses()
                                .stream()
                                .map(response -> new ResponseDto(
                                        response.getId(),
                                        response.getAnswerText()
                                )).collect(java.util.stream.Collectors.toList())
                )).toList();
        return ResponseEntity.ok(questions);
    }

    @Override
    public ResponseEntity<SurveyDto> createQuestionForSurvey(String id, QuestionDto question) {
        Optional<Survey> survey = surveyRepository.findById(id);
        if (survey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Survey surveyFound = survey.get();
        Question questionToSave = new Question();
        questionToSave.setId(UUID.randomUUID().toString());
        questionToSave.setSurvey(surveyFound);
        questionToSave.setQuestionText(question.pregunta());
        questionToSave.setResponses(new ArrayList<>());
        Question questionSaved = questionRepository.save(questionToSave);
        surveyFound.getQuestions().add(questionSaved);
        Survey surveyUpdated = surveyRepository.save(surveyFound);

        List<QuestionDto> questionsDto = surveyUpdated.getQuestions().stream()
                .map(questionDto -> new QuestionDto(
                        questionDto.getId(),
                        questionDto.getQuestionText(),
                        questionDto.getResponses()
                                .stream()
                                .map(response -> new ResponseDto(
                                        response.getId(),
                                        response.getAnswerText()
                                )).collect(java.util.stream.Collectors.toList())
                )).toList();
        SurveyDto surveyDto = new SurveyDto(
                surveyUpdated.getId(),
                surveyUpdated.getTitle(),
                questionsDto
        );
        return ResponseEntity.ok(surveyDto);
    }

    /**
     * Updates an existing survey.
     *
     * @param id the ID of the survey to update
     * @param survey the survey data transfer object containing the updated survey details
     * @return a ResponseEntity containing the updated Survey or a not found status if the survey does not exist
     */
    @Override
    public ResponseEntity<SurveyDto> updateSurvey(String id, SurveyDto survey) {
        Optional<Survey> existSurvey = surveyRepository.findById(id);
        if (existSurvey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Survey surveyToUpdate = existSurvey.get();
        if (survey.titulo() != null && !survey.titulo().isEmpty()) {
            surveyToUpdate.setTitle(survey.titulo());
        }
        if (survey.preguntas() != null && !survey.preguntas().isEmpty()) {
            List<Question> questions = survey.preguntas().stream()
                    .map(question -> {
                        Question questionToSave = new Question();
                        questionToSave.setSurvey(surveyToUpdate);
                        questionToSave.setQuestionText(question.pregunta());
                        if (question.respuestas() != null) {
                            questionToSave.setResponses(question.respuestas()
                                    .stream()
                                    .map(response -> {
                                        Response responseToSave = new Response();
                                        responseToSave.setId(response.idRespuesta());
                                        responseToSave.setAnswerText(response.respuesta());
                                        return responseToSave;
                                    }).toList()
                            );
                        }
                        return questionToSave;
                    }).toList();
            surveyToUpdate.setQuestions(questions);
        }
        Survey surveyUpdated = surveyRepository.save(surveyToUpdate);

        SurveyDto surveyDto = new SurveyDto(
                surveyUpdated.getId(),
                surveyUpdated.getTitle(),
                surveyUpdated.getQuestions().stream()
                        .map(question -> new QuestionDto(
                                question.getId(),
                                question.getQuestionText(),
                                question.getResponses()
                                        .stream()
                                        .map(response -> new ResponseDto(
                                                response.getId(),
                                                response.getAnswerText()
                                        )).collect(java.util.stream.Collectors.toList())
                        )).toList()
        );

        return ResponseEntity.ok(surveyDto);
    }

    /**
     * Deletes a survey by its ID.
     *
     * @param id the ID of the survey to delete
     * @return a ResponseEntity with no content status or a not found status if the survey does not exist
     */
    @Override
    public ResponseEntity<Void> deleteSurvey(String id) {
        Optional<Survey> existSurvey = surveyRepository.findById(id);
        if (existSurvey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        surveyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}