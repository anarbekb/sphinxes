package ru.balmukanov.sphinxes.services.questionnaire;

import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;

public interface FeedbackService {
    void create(CompleteQuestionnaireDto completeQuestionnaireDto);
}
