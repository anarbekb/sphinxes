package ru.balmukanov.sphinxes.services;

import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;

import java.util.List;

public interface QuestionnaireService {
    QuestionnaireDto getQuestionnaire(long id);

    List<QuestionnaireDto> findAll();

    long generateQuestionnaire(CreateQuestionnaireDto request);

    void checkAvailabilityForEdit(long questionnaireId);

    void completeQuestionnaire(CompleteQuestionnaireDto completeQuestionnaireDto);
}
