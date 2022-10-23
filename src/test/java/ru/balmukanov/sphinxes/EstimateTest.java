package ru.balmukanov.sphinxes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;
import ru.balmukanov.sphinxes.services.EstimateService;
import ru.balmukanov.sphinxes.services.EstimateServiceImpl;
import ru.balmukanov.sphinxes.services.QuestionService;

import java.util.List;
import java.util.stream.Stream;

class EstimateTest {
	@Mock
	private QuestionnaireRepository questionnaireRepository;
	@Mock
	private AnswerTopicRepository answerTopicRepository;
	@Mock
	private QuestionService questionService;


	@ParameterizedTest
	@MethodSource("provideArguments")
	void test_estimate(List<Integer> sources, int expected) {
		EstimateService estimateService = new EstimateServiceImpl(questionnaireRepository, answerTopicRepository, questionService);
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
}