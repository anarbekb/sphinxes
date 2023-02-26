package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;
import ru.balmukanov.sphinxes.entities.User;
import ru.balmukanov.sphinxes.services.QuestionnaireService;

@Slf4j
@Controller
@Validated
@RequestMapping("questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    @PostMapping("generate")
    public String generateQuestionnaire(@ModelAttribute @Validated CreateQuestionnaireDto requestQuestionnaire,
                                        Authentication authentication) {
        long questionnaireId = questionnaireService.create(requestQuestionnaire, (User) authentication.getPrincipal());
        return "redirect:/questionnaire/" + questionnaireId;
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("@authService.hasQuestionnaireAccess(#id)")
    public String getQuestionnaire(@PathVariable long id, Model model) {
        QuestionnaireDto questionnaireDto = questionnaireService.getQuestionnaire(id);
        model.addAttribute("questionnaire", questionnaireDto);
        return "questionnaire";
    }

    @PostMapping(value = "complete")
    @PreAuthorize("@authService.hasQuestionnaireAccess(#request.id)")
    public String completeQuestionnaire(@ModelAttribute @Validated CompleteQuestionnaireDto request) {
        questionnaireService.completeQuestionnaire(request);
        return "redirect:/questionnaire/" + request.getId();
    }
}
