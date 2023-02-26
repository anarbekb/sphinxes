package ru.balmukanov.sphinxes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.balmukanov.sphinxes.dto.response.TopicDto;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Topic;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Mapping(target = "id", ignore = true)
    AnswerTopic mapToAnswerTopic(Topic topic);

    TopicDto mapToTopicDto(Topic topic);
}
