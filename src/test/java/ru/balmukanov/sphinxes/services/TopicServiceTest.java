package ru.balmukanov.sphinxes.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.entities.Topic;
import ru.balmukanov.sphinxes.mappers.TopicMapper;
import ru.balmukanov.sphinxes.mappers.TopicMapperImpl;
import ru.balmukanov.sphinxes.repository.TopicRepository;
import ru.balmukanov.sphinxes.services.question.TopicServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TopicServiceTest {
    private final TopicRepository topicRepository = Mockito.mock(TopicRepository.class);
    private final TopicMapper topicMapper = Mockito.spy(TopicMapperImpl.class);

    @Test
    void findByLevels_happyPath() {
        var topic = new Topic();
        topic.setName("Java");
        topic.setQuestions(List.of(createQuestion()));
        when(topicRepository.findByLevels(anyList())).thenReturn(List.of(topic));
        var topicService = new TopicServiceImpl(topicRepository, topicMapper);

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
