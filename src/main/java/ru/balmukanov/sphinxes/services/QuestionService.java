package ru.balmukanov.sphinxes.services;

import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;

public interface QuestionService {
    void create(CreateQuestionDto request);
}
