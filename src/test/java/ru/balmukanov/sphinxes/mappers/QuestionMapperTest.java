package ru.balmukanov.sphinxes.mappers;

import org.junit.jupiter.api.Test;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Question;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMapperTest {
    private final static QuestionMapper MAPPER = new QuestionMapperImpl();

    @Test
    void map_happyPath() {
        var question = new Question();
        question.setId(1);
        question.setTopicId(1);
        question.setAnswer("Test answer");
        question.setLevel(Level.J1);
        question.setSubject("Java");
        question.setPoint("How work hashCode?");
        question.setLinks(null);

        AnswerQuestion answerQuestion = MAPPER.mapToAnswerQuestion(question);

        assertEquals(0L, answerQuestion.getId());
        assertEquals(question.getAnswer(), answerQuestion.getAnswer());
        assertEquals(question.getSubject(), answerQuestion.getSubject());
        assertEquals(question.getLinks(), answerQuestion.getLinks());
        assertEquals(question.getPoint(), answerQuestion.getPoint());
        assertEquals(question.getLevel(), answerQuestion.getLevel());
        assertNull(answerQuestion.getEvaluation());
    }

    @Test
    void map_null() {
        assertNull(MAPPER.mapToAnswerQuestion(null));
    }
}