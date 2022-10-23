package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Feedback;

@Mapper
public interface FeedbackRepository {
    void save(@Param("feedback") Feedback feedback);
}
