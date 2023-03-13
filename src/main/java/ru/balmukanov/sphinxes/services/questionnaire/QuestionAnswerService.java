package ru.balmukanov.sphinxes.services.questionnaire;

import java.util.List;

import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.Question;

public interface QuestionAnswerService {
    void toAnswerQuestionAndSave(List<Question> questions, long answerTopicId);

    void estimate(long answerCommentId, int evaluation);

    AnswerQuestion findById(long id);

    void deleteUnused(long questionnaireId);
}
