package ru.balmukanov.sphinxes.services;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.entities.QuestionnaireStatus;
import ru.balmukanov.sphinxes.exception.ClosedQuestionnaireException;
import ru.balmukanov.sphinxes.mappers.QuestionnaireMapper;
import ru.balmukanov.sphinxes.mappers.QuestionnaireMapperImpl;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

import static org.junit.jupiter.api.Assertions.*;

class QuestionnaireServiceTest {
    private final QuestionnaireRepository questionnaireRepository = Mockito.mock(QuestionnaireRepository.class);
    private final FeedbackService feedbackService = Mockito.mock(FeedbackServiceImpl.class);
    private final QuestionnaireMapper questionnaireMapper = Mockito.spy(QuestionnaireMapperImpl.class);
    private final TopicService topicService = Mockito.mock(TopicService.class);
    private final QuestionAnswerService questionAnswerService = Mockito.mock(QuestionAnswerServiceImpl.class);
    private final ArgumentCaptor<Questionnaire> questionnaireArgumentCaptor = ArgumentCaptor
            .forClass(Questionnaire.class);


    @Test
    void checkAvailabilityForEdit_locked() {
        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setStatus(QuestionnaireStatus.CLOSED);
        Mockito.when(questionnaireRepository.findById(ArgumentMatchers.anyLong())).thenReturn(questionnaire);
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, questionAnswerService);

        assertThrows(ClosedQuestionnaireException.class, () -> questionnaireService.checkAvailabilityForEdit(1L));
    }

    @Test
    void checkAvailabilityForEdit_unlocked() {
        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setStatus(QuestionnaireStatus.PROGRESS);
        Mockito.when(questionnaireRepository.findById(ArgumentMatchers.anyLong())).thenReturn(questionnaire);
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, questionAnswerService);

        assertDoesNotThrow(() -> questionnaireService.checkAvailabilityForEdit(1L));
    }

    @Test
    void completeQuestionnaire_happyPath() {
        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setStatus(QuestionnaireStatus.PROGRESS);
        Mockito.when(questionnaireRepository.findById(ArgumentMatchers.anyLong())).thenReturn(questionnaire);
        Mockito.doNothing().when(questionnaireRepository).save(ArgumentMatchers.isA(Questionnaire.class));
        Mockito.doNothing().when(feedbackService).create(ArgumentMatchers.isA(CompleteQuestionnaireDto.class));
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, questionAnswerService);

        questionnaireService.completeQuestionnaire(new CompleteQuestionnaireDto(1L, "test", "test"));

        Mockito.verify(questionnaireRepository).update(questionnaireArgumentCaptor.capture());
        assertEquals(QuestionnaireStatus.CLOSED, questionnaireArgumentCaptor.getValue().getStatus());
    }
}
