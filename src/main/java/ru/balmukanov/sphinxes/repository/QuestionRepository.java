package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Question;

@Mapper
public interface QuestionRepository {
    void save(@Param("question") Question question);
}
