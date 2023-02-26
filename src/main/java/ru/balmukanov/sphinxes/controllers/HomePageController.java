package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.entities.User;
import ru.balmukanov.sphinxes.services.QuestionnaireService;

@Controller
@RequiredArgsConstructor
public class HomePageController {
    private final QuestionnaireService questionnaireService;

    @GetMapping(value = "/")
    public String homePage(Model model, Authentication authentication) {
        model.addAttribute("requestQuestionnaire", new CreateQuestionnaireDto());
        model.addAttribute("questionnaires", questionnaireService.findByUserWithFeedback(
                (User) authentication.getPrincipal()));
        return "homepage";
    }
}
