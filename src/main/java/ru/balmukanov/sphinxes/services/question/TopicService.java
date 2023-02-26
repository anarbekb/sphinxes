package ru.balmukanov.sphinxes.services.question;

import java.util.List;

import ru.balmukanov.sphinxes.dto.response.TopicDto;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Topic;

public interface TopicService {

    List<Topic> findByLevels(List<Level> levels);

    List<TopicDto> findAll();
}
