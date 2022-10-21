package ru.balmukanov.sphinxes.services;

import java.util.List;
import ru.balmukanov.sphinxes.entities.Question;

public interface QuestionService {
    void toAnswerQuestion(List<Question> questions, long answerTopicId);

    void estimate(long answerCommentId, int evaluation);
}
