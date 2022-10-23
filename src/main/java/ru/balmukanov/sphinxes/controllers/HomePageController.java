package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.services.QuestionnaireService;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    private final QuestionnaireService questionnaireService;

    @GetMapping(value = "/")
    public String homePage(Model model) {
        model.addAttribute("requestQuestionnaire", new CreateQuestionnaireDto());
        model.addAttribute("questionnaires", questionnaireService.findAllWithFeedback());
        return "homepage";
    }
}
