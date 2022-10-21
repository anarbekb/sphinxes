package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Questionnaire;

@Mapper
public interface QuestionnaireRepository {
    Questionnaire findByIdWithTopicsAndQuestions(long id);

    Questionnaire findById(long id);

    void save(@Param("questionnaire") Questionnaire questionnaire);
}
