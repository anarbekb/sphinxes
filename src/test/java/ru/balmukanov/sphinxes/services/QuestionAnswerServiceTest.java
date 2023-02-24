package ru.balmukanov.sphinxes.services;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.mockito.Mockito;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.mappers.QuestionMapper;
import ru.balmukanov.sphinxes.mappers.QuestionMapperImpl;
import ru.balmukanov.sphinxes.repository.AnswerQuestionRepository;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class QuestionAnswerServiceTest {
    private final AnswerQuestionRepository answerQuestionRepository = Mockito.mock(AnswerQuestionRepository.class);
    private final QuestionMapper questionMapper = Mockito.spy(QuestionMapperImpl.class);
    private final ArgumentCaptor<AnswerQuestion> answerQuestionArgumentCaptor = ArgumentCaptor
            .forClass(AnswerQuestion.class);
    private final ArgumentCaptor<Long> answerCommentIdArgumentCaptor = ArgumentCaptor
            .forClass(Long.class);
    private final ArgumentCaptor<Integer> evaluationArgumentCaptor = ArgumentCaptor
            .forClass(Integer.class);

    @Test
    void toAnswerQuestion_happyPath() {
        doNothing().when(answerQuestionRepository).save(isA(AnswerQuestion.class));
        QuestionAnswerService questionAnswerService = new QuestionAnswerServiceImpl(answerQuestionRepository, questionMapper);
        var question = new Question();
        question.setId(1L);
        question.setPoint("Where can JVM work?");
        question.setLevel(Level.M3);
        question.setLinks(null);
        question.setTopicId(1L);
        question.setAnswer("JVM works on all platforms");
        question.setSubject("JVM");

        questionAnswerService.toAnswerQuestionAndSave(List.of(question), 1L);

        verify(answerQuestionRepository).save(answerQuestionArgumentCaptor.capture());
    }

    @Test
    void estimate_happyPath() {
        doNothing().when(answerQuestionRepository).setEvaluation(anyLong(), anyInt());
        QuestionAnswerService questionAnswerService = new QuestionAnswerServiceImpl(answerQuestionRepository, questionMapper);

        questionAnswerService.estimate(1L, 1);

        verify(answerQuestionRepository).setEvaluation(answerCommentIdArgumentCaptor.capture(),
                evaluationArgumentCaptor.capture());
    }
}
