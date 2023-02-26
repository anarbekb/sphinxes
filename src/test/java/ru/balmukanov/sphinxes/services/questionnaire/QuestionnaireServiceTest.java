package ru.balmukanov.sphinxes.services.questionnaire;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.entities.*;
import ru.balmukanov.sphinxes.exception.ClosedQuestionnaireException;
import ru.balmukanov.sphinxes.mappers.QuestionnaireMapper;
import ru.balmukanov.sphinxes.mappers.QuestionnaireMapperImpl;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;
import ru.balmukanov.sphinxes.services.question.TopicService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class QuestionnaireServiceTest {
    private final QuestionnaireRepository questionnaireRepository = Mockito.mock(QuestionnaireRepository.class);
    private final FeedbackService feedbackService = Mockito.mock(FeedbackServiceImpl.class);
    private final QuestionnaireMapper questionnaireMapper = Mockito.spy(QuestionnaireMapperImpl.class);
    private final TopicService topicService = Mockito.mock(TopicService.class);
    private final AnswerTopicService answerTopicService = Mockito.mock(AnswerTopicService.class);
    private final QuestionAnswerService questionAnswerService = Mockito.mock(QuestionAnswerServiceImpl.class);
    private final ArgumentCaptor<Questionnaire> questionnaireArgumentCaptor = ArgumentCaptor
            .forClass(Questionnaire.class);


    @Test
    void checkAvailabilityForEdit_locked() {
        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setStatus(QuestionnaireStatus.CLOSED);
        when(questionnaireRepository.findById(anyLong())).thenReturn(questionnaire);
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, answerTopicService, questionAnswerService);

        assertThrows(ClosedQuestionnaireException.class, () -> questionnaireService.checkAvailabilityForEdit(1L));
    }

    @Test
    void checkAvailabilityForEdit_unlocked() {
        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setStatus(QuestionnaireStatus.PROGRESS);
        when(questionnaireRepository.findById(anyLong())).thenReturn(questionnaire);
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, answerTopicService, questionAnswerService);

        assertDoesNotThrow(() -> questionnaireService.checkAvailabilityForEdit(1L));
    }

    @Test
    void completeQuestionnaire_happyPath() {
        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setStatus(QuestionnaireStatus.PROGRESS);
        when(questionnaireRepository.findById(anyLong())).thenReturn(questionnaire);
        doNothing().when(questionnaireRepository).save(isA(Questionnaire.class));
        doNothing().when(feedbackService).create(isA(CompleteQuestionnaireDto.class));
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, answerTopicService, questionAnswerService);

        questionnaireService.completeQuestionnaire(new CompleteQuestionnaireDto(1L, "test", "test"));

        verify(questionnaireRepository).update(questionnaireArgumentCaptor.capture());
        assertEquals(QuestionnaireStatus.CLOSED, questionnaireArgumentCaptor.getValue().getStatus());
    }

    @Test
    void create_happyPath() {
        when(topicService.findByLevels(anyList())).thenReturn(List.of(createTopic()));
        when(answerTopicService.toAnswer(isA(Topic.class), anyLong())).thenReturn(createAnswerTopic());
        doNothing().when(questionAnswerService).toAnswerQuestionAndSave(anyList(), anyLong());
        doNothing().when(questionnaireRepository).save(isA(Questionnaire.class));
        var creator = new User();
        var questionnaireService = new QuestionnaireServiceImpl(questionnaireRepository, feedbackService,
                questionnaireMapper, topicService, answerTopicService, questionAnswerService);

        questionnaireService.create(new CreateQuestionnaireDto("Ivanov Ivan",
                "Test project name", Level.M1.name()), creator);

        verify(answerTopicService).toAnswer(isA(Topic.class), anyLong());
        verify(questionAnswerService).toAnswerQuestionAndSave(anyList(), anyLong());
        verify(questionnaireRepository).save(questionnaireArgumentCaptor.capture());
        assertEquals(creator, questionnaireArgumentCaptor.getValue().getCreator());

    }

    private Topic createTopic() {
        var question = new Question();
        question.setId(1);
        question.setTopicId(1);
        question.setAnswer("Test answer");
        question.setLevel(Level.J1);
        question.setSubject("Database indexes");
        question.setPoint("How work indexes?");
        question.setLinks(null);

        var topic = new Topic();
        topic.setId(1L);
        topic.setName("Database");
        topic.setQuestions(List.of(question));

        return topic;
    }

    private AnswerTopic createAnswerTopic() {
        var answerTopic = new AnswerTopic();
        answerTopic.setId(1L);
        answerTopic.setName("Database");
        answerTopic.setQuestionnaireId(1L);

        return answerTopic;
    }
}
