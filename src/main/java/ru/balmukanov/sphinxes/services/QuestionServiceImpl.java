package ru.balmukanov.sphinxes.services;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.mappers.QuestionMapper;
import ru.balmukanov.sphinxes.repository.AnswerQuestionRepository;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final AnswerQuestionRepository answerQuestionRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public void toAnswerQuestion(List<Question> questions, long answerTopicId) {
        questions.stream().map(questionMapper::mapToAnswerQuestion).forEach(answerQuestion -> {
            answerQuestion.setAnswerTopicId(answerTopicId);
            answerQuestionRepository.save(answerQuestion);
        });
    }

    @Override
    @Transactional
    public void estimate(long answerCommentId, int evaluation) {
        answerQuestionRepository.setEvaluation(answerCommentId, evaluation);
    }
}
