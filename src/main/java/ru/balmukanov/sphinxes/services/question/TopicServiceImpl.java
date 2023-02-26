package ru.balmukanov.sphinxes.services.question;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balmukanov.sphinxes.dto.response.TopicDto;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Topic;
import ru.balmukanov.sphinxes.mappers.TopicMapper;
import ru.balmukanov.sphinxes.repository.TopicRepository;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    @Override
    public List<Topic> findByLevels(List<Level> levels) {
        return topicRepository.findByLevels(levels);
    }

    @Override
    public List<TopicDto> findAll() {
        return topicRepository.findAll().stream()
                .map(topicMapper::mapToTopicDto)
                .toList();
    }
}
