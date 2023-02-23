package ru.balmukanov.sphinxes.services;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.entities.Topic;
import ru.balmukanov.sphinxes.mappers.TopicMapper;
import ru.balmukanov.sphinxes.mappers.TopicMapperImpl;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;
import ru.balmukanov.sphinxes.repository.TopicRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class TopicServiceTest {
    private final AnswerTopicRepository answerTopicRepository = Mockito.mock(AnswerTopicRepository.class);
    private final TopicRepository topicRepository = Mockito.mock(TopicRepository.class);
    private final TopicMapper topicMapper = Mockito.spy(TopicMapperImpl.class);
    private final ArgumentCaptor<AnswerTopic> answerTopicArgumentCaptor = ArgumentCaptor.forClass(AnswerTopic.class);

    @Test
    void toAnswer_happyPath() {
        doNothing().when(answerTopicRepository).save(isA(AnswerTopic.class));
        var topicService = new TopicServiceImpl(answerTopicRepository, topicRepository, topicMapper);
        var topic = new Topic();
        topic.setName("Java");
        topic.setQuestions(List.of(createQuestion()));
        var questionnaireId = 1L;

        AnswerTopic answerTopic = topicService.toAnswer(topic, questionnaireId);

        verify(answerTopicRepository).save(answerTopicArgumentCaptor.capture());
        assertEquals(topic.getName(), answerTopic.getName());
        assertEquals(topic.getQuestions().size(), answerTopic.getQuestions().size());
        assertEquals(questionnaireId, answerTopic.getQuestionnaireId());
    }

    @Test
    void findByLevels_happyPath() {
        var topic = new Topic();
        topic.setName("Java");
        topic.setQuestions(List.of(createQuestion()));
        when(topicRepository.findByLevels(anyList())).thenReturn(List.of(topic));
        var topicService = new TopicServiceImpl(answerTopicRepository, topicRepository, topicMapper);

        List<Topic> topics = topicService.findByLevels(List.of(Level.M3));

        assertEquals(1, topics.size());
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
