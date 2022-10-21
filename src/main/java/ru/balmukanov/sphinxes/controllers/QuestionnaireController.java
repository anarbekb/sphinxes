package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;
import ru.balmukanov.sphinxes.services.QuestionnaireService;

@Slf4j
@Controller
@RequestMapping("questionnaire")
@RequiredArgsConstructor
public class QuestionnaireController {
    private final QuestionnaireService questionnaireService;

    @PostMapping("generate")
    public String generateQuestion(@ModelAttribute CreateQuestionnaireDto requestQuestionnaire) {
        long questionnaireId = questionnaireService.generateQuestionnaire(requestQuestionnaire);
        return "redirect:/questionnaire/" + questionnaireId;
    }

    @GetMapping(value = "{id}")
    public String getQuestion(@PathVariable long id, Model model) {
        QuestionnaireDto questionnaireDto = questionnaireService.getQuestionnaire(id);
        model.addAttribute("questionnaire", questionnaireDto);
        return "questionnaire";
    }
}
