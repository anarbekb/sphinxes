package ru.balmukanov.sphinxes.services.question;

import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;

public interface QuestionService {
    void create(CreateQuestionDto request);
}
