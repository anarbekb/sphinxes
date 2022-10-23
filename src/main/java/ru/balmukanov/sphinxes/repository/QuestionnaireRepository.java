package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Questionnaire;

import java.util.List;

@Mapper
public interface QuestionnaireRepository {
    Questionnaire findByIdWithTopicsAndQuestions(long id);

    Questionnaire findById(long id);

    List<Questionnaire> findAllWithFeedback();

    void save(@Param("questionnaire") Questionnaire questionnaire);

    void update(@Param("questionnaire") Questionnaire questionnaire);
}
