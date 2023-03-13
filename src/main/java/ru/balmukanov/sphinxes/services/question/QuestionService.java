package ru.balmukanov.sphinxes.services.question;

import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;
import ru.balmukanov.sphinxes.dto.response.QuestionDto;

import java.util.List;

public interface QuestionService {
    void create(CreateQuestionDto request);

    List<QuestionDto> findAll();
}
