package ru.balmukanov.sphinxes.services.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.balmukanov.sphinxes.entities.*;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class AuthServiceTest {
    private final QuestionnaireRepository questionnaireRepository = Mockito.mock(QuestionnaireRepository.class);
    private final Authentication authentication = Mockito.mock(Authentication.class);
    private final SecurityContext securityContext = Mockito.mock(SecurityContext.class);

    @Test
    void hasQuestionnaireAccess_happyPath() {
        Questionnaire questionnaire = createQuestionnaire();
        when(questionnaireRepository.findByIdWithUser(anyLong())).thenReturn(questionnaire);
        when(authentication.getPrincipal()).thenReturn(questionnaire.getCreator());
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        var authService = new AuthService(questionnaireRepository);

        assertTrue(authService.hasQuestionnaireAccess(1L));
    }

    @Test
    void hasQuestionnaireAccess_noAccess() {
        var user = new User();
        user.setId(2L);
        user.setUsername("foo");
        user.setPassword("test");
        when(questionnaireRepository.findByIdWithUser(anyLong())).thenReturn(createQuestionnaire());
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        var authService = new AuthService(questionnaireRepository);

        assertFalse(authService.hasQuestionnaireAccess(1L));
    }

    private Questionnaire createQuestionnaire() {
        var question = new AnswerQuestion();
        question.setId(3L);
        question.setAnswerTopicId(2L);
        question.setEvaluation(2);
        question.setPoint("How work Indexes?");
        question.setLinks(null);
        question.setAnswer("Test answer");
        question.setSubject("Database indexes");
        question.setLevel(Level.M1);

        var answerTopic = new AnswerTopic();
        answerTopic.setId(2L);
        answerTopic.setName("Database");
        answerTopic.setQuestionnaireId(1L);
        answerTopic.setEvaluation(2);
        answerTopic.setQuestions(List.of(question));

        var creator = new User();
        creator.setId(1L);
        creator.setPassword("");
        creator.setActive(true);
        creator.setUsername("test");

        var questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setEvaluation(null);
        questionnaire.setCreatedDt(Instant.now());
        questionnaire.setStatus(QuestionnaireStatus.PROGRESS);
        questionnaire.setProject("Test company name");
        questionnaire.setCandidate("Ivanov Ivan");
        questionnaire.setCreator(creator);
        questionnaire.setTopics(List.of(answerTopic));

        return questionnaire;
    }
}
