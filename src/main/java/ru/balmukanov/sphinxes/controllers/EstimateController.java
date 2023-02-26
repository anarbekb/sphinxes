package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.balmukanov.sphinxes.dto.request.EstimateDto;
import ru.balmukanov.sphinxes.exception.ClosedQuestionnaireException;
import ru.balmukanov.sphinxes.services.EstimateService;
import ru.balmukanov.sphinxes.services.QuestionnaireService;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
public class EstimateController {
    private final QuestionnaireService questionnaireService;
    private final EstimateService estimateService;

    @PostMapping("estimate")
    @PreAuthorize("@authService.hasQuestionnaireAccess(#request.questionnaireId)")
    public ResponseEntity<HttpStatus> estimate(@RequestBody @Validated EstimateDto request) {
        try {
            questionnaireService.checkAvailabilityForEdit(request.getQuestionnaireId());
            estimateService.estimate(request);
            return ResponseEntity.ok().build();
        } catch (ClosedQuestionnaireException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).build();
        } catch (Exception e) {
            log.error("Error set estimate", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
