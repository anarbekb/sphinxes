package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;

import java.util.List;

@Mapper
public interface AnswerQuestionRepository {
    void save(@Param("answerQuestion") AnswerQuestion answerQuestion);

    void setEvaluation(long id, int evaluation);

    List<AnswerQuestion> findFailed(long questionnaireId);
}
