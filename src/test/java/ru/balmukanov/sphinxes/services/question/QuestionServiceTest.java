package ru.balmukanov.sphinxes.services.question;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionDto;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Question;
import ru.balmukanov.sphinxes.mappers.QuestionMapper;
import ru.balmukanov.sphinxes.mappers.QuestionMapperImpl;
import ru.balmukanov.sphinxes.repository.QuestionRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class QuestionServiceTest {
    private final QuestionRepository questionRepository = Mockito.mock(QuestionRepository.class);
    private final QuestionMapper questionMapper = Mockito.spy(QuestionMapperImpl.class);
    private final ArgumentCaptor<Question> questionArgumentCaptor = ArgumentCaptor.forClass(Question.class);

    @Test
    void create_happyPath() {
        doNothing().when(questionRepository).save(isA(Question.class));
        var request = new CreateQuestionDto("How work hashCode?", "Test answer", 1L,
                "Java", Level.J1.name(), null);
        var questionService = new QuestionServiceImpl(questionRepository, questionMapper);

        questionService.create(request);

        verify(questionRepository).save(questionArgumentCaptor.capture());
        Question question = questionArgumentCaptor.getValue();
        assertEquals(request.getSubject(), question.getSubject());
        assertEquals(request.getAnswer(), question.getAnswer());
        assertEquals(request.getPoint(), question.getPoint());
        assertEquals(request.getLinks(), question.getLinks());
        assertEquals(request.getTopicId(), question.getTopicId());
        assertEquals(Level.valueOf(request.getLevel()), question.getLevel());
    }
}
