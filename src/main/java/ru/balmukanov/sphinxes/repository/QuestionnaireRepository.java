package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.entities.QuestionnaireStatus;

import java.util.List;

@Mapper
public interface QuestionnaireRepository {
    Questionnaire findByIdWithTopicsAndQuestions(long id);

    Questionnaire findById(long id);

    List<Questionnaire> findAll();

    void save(@Param("questionnaire") Questionnaire questionnaire);

    void changeStatus(long id, QuestionnaireStatus status);//todo replace with update

    void update(@Param("questionnaire") Questionnaire questionnaire);
}
