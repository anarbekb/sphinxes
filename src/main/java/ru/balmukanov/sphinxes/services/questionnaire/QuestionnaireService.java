package ru.balmukanov.sphinxes.services.questionnaire;

import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;
import ru.balmukanov.sphinxes.entities.User;

import java.util.List;

public interface QuestionnaireService {
    QuestionnaireDto getQuestionnaire(long id);

    List<QuestionnaireDto> findByUserWithFeedback(User principal);

    long create(CreateQuestionnaireDto request, User principal);

    void checkAvailabilityForEdit(long questionnaireId);

    void completeQuestionnaire(CompleteQuestionnaireDto completeQuestionnaireDto);
}
