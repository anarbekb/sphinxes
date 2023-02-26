package ru.balmukanov.sphinxes.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Topic;

@Mapper
public interface TopicRepository {
    List<Topic> findByLevels(List<Level> levels);

    List<Topic> findAll();
}
