package ru.balmukanov.sphinxes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;
import ru.balmukanov.sphinxes.dto.response.QuestionDto;
import ru.balmukanov.sphinxes.services.question.QuestionService;
import ru.balmukanov.sphinxes.services.question.TopicService;

@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final TopicService topicService;

    @GetMapping("/questions")
    public String questions(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "question-list";
    }

    @GetMapping("/create-question")
    public String createQuestion(Model model) {
        model.addAttribute("requestQuestion", new CreateQuestionDto());
        model.addAttribute("topics", topicService.findAll());
        return "create-question";
    }

    @PostMapping("/create-question")
    public String createQuestion(@ModelAttribute @Validated CreateQuestionDto requestQuestion) {
        questionService.create(requestQuestion);
        return "redirect:/";
    }

    @GetMapping("/edit-question/{id}")
    public String editQuestion(@PathVariable("id") long id, Model model) {
        model.addAttribute("question", questionService.findById(id));
        model.addAttribute("topics", topicService.findAll());
        return "edit-question";
    }

    @PostMapping("/edit-question/{id}")
    public String editQuestion(@PathVariable("id") long id, @ModelAttribute @Validated QuestionDto questionDto) {
        questionService.edit(id, questionDto);
        return "redirect:/questions";
    }
}
