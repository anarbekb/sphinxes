package ru.balmukanov.sphinxes.services;

import java.util.List;

import ru.balmukanov.sphinxes.dto.response.TopicDto;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Topic;

public interface TopicService {
    AnswerTopic toAnswer(Topic topic, long questionnaireId);

    List<Topic> findByLevels(List<Level> levels);

    List<TopicDto> findAll();
}
