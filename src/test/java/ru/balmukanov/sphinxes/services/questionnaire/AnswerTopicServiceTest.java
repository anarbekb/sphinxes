package ru.balmukanov.sphinxes.services.questionnaire;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.entities.Topic;
import ru.balmukanov.sphinxes.mappers.TopicMapper;
import ru.balmukanov.sphinxes.mappers.TopicMapperImpl;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class AnswerTopicServiceTest {
    private final AnswerTopicRepository answerTopicRepository = Mockito.mock(AnswerTopicRepository.class);
    private final TopicMapper topicMapper = Mockito.spy(TopicMapperImpl.class);

    @Test
    void toAnswer_happyPath() {
        doNothing().when(answerTopicRepository).save(isA(AnswerTopic.class));
        var topicService = new AnswerTopicServiceImpl(answerTopicRepository, topicMapper);
        var topic = new Topic();
        topic.setName("Java");
        topic.setQuestions(List.of(createQuestion()));
        var questionnaireId = 1L;

        AnswerTopic answerTopic = topicService.toAnswer(topic, questionnaireId);

        verify(answerTopicRepository).save(isA(AnswerTopic.class));
        assertEquals(topic.getName(), answerTopic.getName());
        assertEquals(topic.getQuestions().size(), answerTopic.getQuestions().size());
        assertEquals(questionnaireId, answerTopic.getQuestionnaireId());
    }

    private Question createQuestion() {
        var question = new Question();
        question.setId(1L);
        question.setPoint("Where can JVM work?");
        question.setLevel(Level.M3);
        question.setLinks(null);
        question.setTopicId(1L);
        question.setAnswer("JVM works on all platforms");
        question.setSubject("JVM");

        return question;
    }
}
