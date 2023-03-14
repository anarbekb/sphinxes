package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.AnswerTopic;

@Mapper
public interface AnswerTopicRepository {
    void save(@Param("answerTopic") AnswerTopic answerTopic);

    void update(@Param("answerTopic") AnswerTopic answerTopic);

    void deleteUnused(long questionnaireId);
}
