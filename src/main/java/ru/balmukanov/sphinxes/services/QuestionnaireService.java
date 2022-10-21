package ru.balmukanov.sphinxes.services;

import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;

public interface QuestionnaireService {
    long generateQuestionnaire(CreateQuestionnaireDto request);

    QuestionnaireDto getQuestionnaire(long id);

    void checkAvailabilityForEdit(long questionnaireId);
}
