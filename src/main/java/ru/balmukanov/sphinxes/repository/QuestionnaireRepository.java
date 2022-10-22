package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.entities.QuestionnaireStatus;

@Mapper
public interface QuestionnaireRepository {
    Questionnaire findByIdWithTopicsAndQuestions(long id);

    Questionnaire findById(long id);

    void save(@Param("questionnaire") Questionnaire questionnaire);

    void changeStatus(long id, QuestionnaireStatus status);
}
