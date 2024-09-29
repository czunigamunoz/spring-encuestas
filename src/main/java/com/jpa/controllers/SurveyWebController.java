package com.jpa.controllers;

import com.jpa.dtos.QuestionDto;
import com.jpa.dtos.ResponseDto;
import com.jpa.dtos.SurveyDto;
import com.jpa.services.IQuestionService;
import com.jpa.services.ISurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/encuestas")
public class SurveyWebController {

    private static final String REDIRECT_SURVEYS = "redirect:/encuestas";
    private static final String REDIRECT_QUESTIONS = "redirect:/encuestas/preguntas";
    private static final String REDIRECT_RESPONSES = "redirect:/encuestas/preguntas/respuestas";
    private final ISurveyService surveyService;
    private final IQuestionService questionService;

    @GetMapping()
    public String getAllSurveys(Model model) {
        model.addAttribute("encuestas", surveyService.getAllSurveys().getBody());
        return "encuestas/lista";
    }

    @GetMapping("/nuevo")
    public String createSurveyForm(Model model) {
        model.addAttribute("encuesta", new SurveyDto());
        return "encuestas/formulario";
    }

    @PostMapping("/nuevo")
    public String createSurvey(@RequestBody SurveyDto surveyDto) {
        surveyService.createSurvey(surveyDto);
        return REDIRECT_SURVEYS;
    }

    @GetMapping("/editar/{idEncuesta}")
    public String updateSurveyForm(@PathVariable String idEncuesta, Model model) {
        SurveyDto survey = surveyService.getById(idEncuesta).getBody();
        model.addAttribute("encuesta", survey);
        return "encuestas/formulario";
    }

    @PostMapping("/editar/{idEncuesta}")
    public String updateSurvey(@PathVariable String idEncuesta, @RequestBody SurveyDto survey) {
        surveyService.updateSurvey(idEncuesta, survey);
        return REDIRECT_SURVEYS;
    }

    @DeleteMapping("/eliminar/{idEncuesta}")
    public String deleteSurvey(@PathVariable String idEncuesta) {
        surveyService.deleteSurvey(idEncuesta);
        return REDIRECT_SURVEYS;
    }

    @GetMapping("/preguntas/nueva/{idEncuesta}")
    public String createQuestionForm(@PathVariable String idEncuesta, Model model) {
        model.addAttribute("pregunta", new QuestionDto());
        return "encuestas/preguntas/formulario";
    }

    @PostMapping("/preguntas/nueva/{idEncuesta}")
    public String createQuestion(@PathVariable String idEncuesta, @ModelAttribute QuestionDto questionDto) {
        surveyService.createQuestionForSurvey(idEncuesta, questionDto);
        return REDIRECT_QUESTIONS;
    }

    @GetMapping("/preguntas/{id}/respuestas")
    public String getAllResponses(@PathVariable String id,  Model model) {
        model.addAttribute("respuestas", questionService.getResponsesByQuestion(id).getBody());
        return "encuestas/preguntas/respuestas/lista";
    }

    @GetMapping("/preguntas/{id}/respuestas/nueva")
    public String createResponseForm(Model model) {
        model.addAttribute("respuesta", new ResponseDto());
        return "encuestas/preguntas/respuestas/formulario";
    }

    @PostMapping("/preguntas/{id}/respuestas/nueva")
    public String createResponse(@PathVariable String id, @ModelAttribute ResponseDto responseDto) {
        questionService.createQuestionResponse(id, responseDto);
        return REDIRECT_RESPONSES;
    }
}
