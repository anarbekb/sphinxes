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
import ru.balmukanov.sphinxes.services.QuestionAnswerService;
import ru.balmukanov.sphinxes.services.QuestionAnswerServiceImpl;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class EstimateServiceTest {
	private final QuestionnaireRepository questionnaireRepository = Mockito.mock(QuestionnaireRepository.class);
	private final AnswerTopicRepository answerTopicRepository = Mockito.mock(AnswerTopicRepository.class);
	private final QuestionAnswerService questionAnswerService = Mockito.mock(QuestionAnswerServiceImpl.class);

	@Test
	void estimate_happyPath() {
		Questionnaire questionnaire = createQuestionnaire();
		when(questionAnswerService.findById(ArgumentMatchers.anyLong())).thenReturn(
				questionnaire.getTopics().stream()
						.flatMap(answerTopic -> answerTopic.getQuestions().stream())
						.filter(answerQuestion -> answerQuestion.getId() == 1)
						.findFirst().orElseThrow());
		doNothing().when(questionAnswerService).estimate(ArgumentMatchers.anyLong(), ArgumentMatchers.anyInt());
		doNothing().when(answerTopicRepository).update(ArgumentMatchers.isA(AnswerTopic.class));
		doNothing().when(questionnaireRepository).update(ArgumentMatchers.isA(Questionnaire.class));
		when(questionnaireRepository.findByIdFullRelationsMapped(ArgumentMatchers.anyLong()))
				.thenReturn(questionnaire);
		var estimateService = new EstimateServiceImpl(questionnaireRepository, answerTopicRepository, questionAnswerService);

		var estimateDto = new EstimateDto();
		estimateDto.setEvaluation(4);
		estimateDto.setQuestionId(1L);
		estimateDto.setQuestionnaireId(1L);
		estimateService.estimate(estimateDto);

		Mockito.verify(answerTopicRepository).update(isA(AnswerTopic.class));
		Mockito.verify(questionnaireRepository).update(isA(Questionnaire.class));
	}

	@ParameterizedTest
	@MethodSource("provideArguments")
	void estimate_test(List<Integer> sources, int expected) {
		var estimateService = new EstimateServiceImpl(questionnaireRepository, answerTopicRepository, questionAnswerService);
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
		var answerQuestionHashCode = new AnswerQuestion();
		answerQuestionHashCode.setId(1L);
		answerQuestionHashCode.setAnswerTopicId(1L);
		answerQuestionHashCode.setEvaluation(4);
		answerQuestionHashCode.setPoint("How work hashCode?");
		answerQuestionHashCode.setLinks(null);
		answerQuestionHashCode.setAnswer("Test answer");
		answerQuestionHashCode.setSubject("hashCode");
		answerQuestionHashCode.setLevel(Level.M1);

		var answerQuestionHashMap = new AnswerQuestion();
		answerQuestionHashMap.setId(2L);
		answerQuestionHashMap.setAnswerTopicId(1L);
		answerQuestionHashMap.setEvaluation(3);
		answerQuestionHashMap.setPoint("How work HashMap?");
		answerQuestionHashMap.setLinks(null);
		answerQuestionHashMap.setAnswer("Test answer");
		answerQuestionHashMap.setSubject("HashMap");
		answerQuestionHashMap.setLevel(Level.M1);

		var answerTopicJava = new AnswerTopic();
		answerTopicJava.setId(1L);
		answerTopicJava.setName("Java");
		answerTopicJava.setQuestionnaireId(1L);
		answerTopicJava.setEvaluation(3);
		answerTopicJava.setQuestions(List.of(answerQuestionHashCode, answerQuestionHashMap));

		var answerQuestionIndexes = new AnswerQuestion();
		answerQuestionIndexes.setId(3L);
		answerQuestionIndexes.setAnswerTopicId(2L);
		answerQuestionIndexes.setEvaluation(2);
		answerQuestionIndexes.setPoint("How work Indexes?");
		answerQuestionIndexes.setLinks(null);
		answerQuestionIndexes.setAnswer("Test answer");
		answerQuestionIndexes.setSubject("Database indexes");
		answerQuestionIndexes.setLevel(Level.M1);

		var answerTopicDatabase = new AnswerTopic();
		answerTopicDatabase.setId(2L);
		answerTopicDatabase.setName("Database");
		answerTopicDatabase.setQuestionnaireId(1L);
		answerTopicDatabase.setEvaluation(2);
		answerTopicDatabase.setQuestions(List.of(answerQuestionIndexes));

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
		questionnaire.setTopics(List.of(answerTopicJava, answerTopicDatabase));

		return questionnaire;
	}
}
