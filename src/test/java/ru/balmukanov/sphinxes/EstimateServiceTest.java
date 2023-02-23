package ru.balmukanov.sphinxes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ru.balmukanov.sphinxes.dto.request.EstimateDto;
import ru.balmukanov.sphinxes.entities.*;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;
import ru.balmukanov.sphinxes.services.EstimateServiceImpl;
import ru.balmukanov.sphinxes.services.QuestionService;
import ru.balmukanov.sphinxes.services.QuestionServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class EstimateServiceTest {
	private final QuestionnaireRepository questionnaireRepository = Mockito.mock(QuestionnaireRepository.class);
	private final AnswerTopicRepository answerTopicRepository = Mockito.mock(AnswerTopicRepository.class);
	private final QuestionService questionService = Mockito.mock(QuestionServiceImpl.class);

	@Test
	void estimate_happyPath() {
		doNothing().when(questionService).estimate(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
		doNothing().when(answerTopicRepository).update(ArgumentMatchers.isA(AnswerTopic.class));
		doNothing().when(questionnaireRepository).update(ArgumentMatchers.isA(Questionnaire.class));
		when(questionnaireRepository.findByIdWithTopicsAndQuestions(ArgumentMatchers.anyLong()))
				.thenReturn(createQuestionnaire());
		var estimateService = new EstimateServiceImpl(questionnaireRepository, answerTopicRepository, questionService);

		var estimateDto = new EstimateDto();
		estimateDto.setEvaluation(4);
		estimateDto.setQuestionId(1L);
		estimateDto.setQuestionnaireId(1L);
		estimateService.estimate(estimateDto);
	}

	@ParameterizedTest
	@MethodSource("provideArguments")
	void estimate_test(List<Integer> sources, int expected) {
		var estimateService = new EstimateServiceImpl(questionnaireRepository, answerTopicRepository, questionService);
		Assertions.assertEquals(expected, estimateService.estimate(sources));
	}

	private static Stream<Arguments> provideArguments() {
		return Stream.of(
			Arguments.of(List.of(4, 4, 1, 2), 1),
			Arguments.of(List.of(1, 1, 4, 4), 1),
			Arguments.of(List.of(1, 1, 2, 2), 1),
			Arguments.of(List.of(1, 2, 2, 3, 4), 1),
			Arguments.of(List.of(1, 2, 3, 4), 1),
			Arguments.of(List.of(2, 3, 3, 3), 2),
			Arguments.of(List.of(2, 4, 4, 4), 2),
			Arguments.of(List.of(1, 2, 2, 2, 3, 3, 3, 3, 4, 4), 2),
			Arguments.of(List.of(2, 3, 3, 3, 3), 3),
			Arguments.of(List.of(2, 4, 4, 4, 4), 4),
			Arguments.of(List.of(3, 4), 3),
			Arguments.of(List.of(3, 4, 4), 4),
			Arguments.of(List.of(3, 4, 4, 4), 4),
			Arguments.of(List.of(3, 4, 4, 4, 4), 4)
		);
	}

	private Questionnaire createQuestionnaire() {
		var answerQuestion = new AnswerQuestion();
		answerQuestion.setId(1L);
		answerQuestion.setAnswerTopicId(1L);
		answerQuestion.setEvaluation(4);
		answerQuestion.setPoint("How work hashCode?");
		answerQuestion.setLinks(null);
		answerQuestion.setAnswer("Test answer");
		answerQuestion.setSubject("hashCode");
		answerQuestion.setLevel(Level.M1);

		var answerTopic = new AnswerTopic();
		answerTopic.setId(1L);
		answerTopic.setName("Java");
		answerTopic.setQuestionnaireId(1L);
		answerTopic.setEvaluation(null);
		answerTopic.setQuestions(List.of(answerQuestion));

		var questionnaire = new Questionnaire();
		questionnaire.setId(1L);
		questionnaire.setEvaluation(null);
		questionnaire.setCreatedDt(Instant.now());
		questionnaire.setStatus(QuestionnaireStatus.PROGRESS);
		questionnaire.setProject("Test company name");
		questionnaire.setCandidate("Ivanov Ivan");
		questionnaire.setTopics(List.of(answerTopic));

		return questionnaire;
	}
}
