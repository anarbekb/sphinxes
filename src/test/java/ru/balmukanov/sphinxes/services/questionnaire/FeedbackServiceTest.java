package ru.balmukanov.sphinxes.services.questionnaire;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.Feedback;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.repository.AnswerQuestionRepository;
import ru.balmukanov.sphinxes.repository.FeedbackRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackServiceTest {
    private final FeedbackRepository feedbackRepository = Mockito.mock(FeedbackRepository.class);
    private final AnswerQuestionRepository answerQuestionRepository = Mockito.mock(AnswerQuestionRepository.class);
    private final ArgumentCaptor<Feedback> feedbackArgumentCaptor = ArgumentCaptor.forClass(Feedback.class);

    @Test
    void create_happyPath() {
        Mockito.when(answerQuestionRepository.findFailed(ArgumentMatchers.anyLong()))
                .thenReturn(createAnswerQuestions());
        Mockito.doNothing().when(feedbackRepository).save(ArgumentMatchers.isA(Feedback.class));
        var feedbackService = new FeedbackServiceImpl(feedbackRepository, answerQuestionRepository);

        feedbackService.create(new CompleteQuestionnaireDto(1L, "Test", "Test"));

        Mockito.verify(feedbackRepository).save(feedbackArgumentCaptor.capture());
        Feedback feedback = feedbackArgumentCaptor.getValue();
        assertEquals("hashCode: http://localhost/hashcode , HashMap: http://localhost/hashmap",
                feedback.getNeedImprove());
        assertEquals("Test", feedback.getStrengths());
        assertEquals("Test", feedback.getWeaknesses());
        assertEquals(1L, feedback.getQuestionnaireId());
    }

    private List<AnswerQuestion> createAnswerQuestions() {
        var answerQuestionHashCode = new AnswerQuestion();
        answerQuestionHashCode.setId(1L);
        answerQuestionHashCode.setAnswerTopicId(1L);
        answerQuestionHashCode.setEvaluation(1);
        answerQuestionHashCode.setPoint("How work hashCode?");
        answerQuestionHashCode.setLinks("http://localhost/hashcode");
        answerQuestionHashCode.setAnswer("Test answer");
        answerQuestionHashCode.setSubject("hashCode");
        answerQuestionHashCode.setLevel(Level.M1);

        var answerQuestionHashMap = new AnswerQuestion();
        answerQuestionHashMap.setId(2L);
        answerQuestionHashMap.setAnswerTopicId(1L);
        answerQuestionHashMap.setEvaluation(1);
        answerQuestionHashMap.setPoint("How work HashMap?");
        answerQuestionHashMap.setLinks("http://localhost/hashmap");
        answerQuestionHashMap.setAnswer("Test answer");
        answerQuestionHashMap.setSubject("HashMap");
        answerQuestionHashMap.setLevel(Level.M1);

        return List.of(answerQuestionHashCode, answerQuestionHashMap);
    }
}
