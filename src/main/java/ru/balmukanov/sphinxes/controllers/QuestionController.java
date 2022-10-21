package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.balmukanov.sphinxes.dto.request.EstimateDto;
import ru.balmukanov.sphinxes.exception.ClosedQuestionnaireException;
import ru.balmukanov.sphinxes.services.QuestionService;
import ru.balmukanov.sphinxes.services.QuestionnaireService;

@Slf4j
@Controller
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionnaireService questionnaireService;
    private final QuestionService questionService;

    @PostMapping("estimate/{questionId}")
    public ResponseEntity<HttpStatus> estimate(@PathVariable long questionId, @RequestBody EstimateDto request) {
        try {
            questionnaireService.checkAvailabilityForEdit(request.getQuestionnaireId());
            questionService.estimate(questionId, request.getEvaluation());
            return ResponseEntity.ok().build();
        } catch (ClosedQuestionnaireException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        } catch (Exception e) {
            log.error("Error set estimate", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
