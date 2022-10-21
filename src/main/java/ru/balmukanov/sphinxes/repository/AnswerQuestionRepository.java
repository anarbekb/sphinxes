package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;

@Mapper
public interface AnswerQuestionRepository {
    void save(@Param("answerQuestion") AnswerQuestion answerQuestion);

    void estimate(long id, int evaluation);
}
