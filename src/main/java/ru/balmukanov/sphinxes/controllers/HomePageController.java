package ru.balmukanov.sphinxes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;

@Controller
public class HomePageController {
    @GetMapping(value = "/")
    public String homePage(Model model) {
        model.addAttribute("requestQuestionnaire", new CreateQuestionnaireDto());
        return "homepage";
    }
}
