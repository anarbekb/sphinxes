package ru.balmukanov.sphinxes.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.balmukanov.sphinxes.entities.Questionnaire;

import java.util.List;

@Mapper
public interface QuestionnaireRepository {
    /**
     * select questionnaire with topics(include questions), feedback, creator
     */
    Questionnaire findByIdFullRelationsMapped(long id);

    Questionnaire findByIdWithUser(long id);

    /**
     * select only questionnaire
     */
    Questionnaire findById(long id);

    /**
     * select questionnaire with feedback
     */
    List<Questionnaire> findByUser(long userId);

    void save(@Param("questionnaire") Questionnaire questionnaire);

    void update(@Param("questionnaire") Questionnaire questionnaire);
}
