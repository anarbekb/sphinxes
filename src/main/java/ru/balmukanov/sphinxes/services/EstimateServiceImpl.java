package ru.balmukanov.sphinxes.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.dto.request.EstimateDto;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.exception.AnswerTopicNotFoundException;
import ru.balmukanov.sphinxes.exception.EstimateException;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
	private final static int NONE_ESTIMATE = 1;
	private final static int INTERN_ESTIMATE = 2;
	private final static int NOTICE_ESTIMATE = 3;
	private final static int ADVANCED_ESTIMATE = 4;
	private final QuestionnaireRepository questionnaireRepository;
	private final AnswerTopicRepository answerTopicRepository;
	private final QuestionAnswerService questionAnswerService;

	@Override
	@Transactional
	public void estimate(EstimateDto estimateDto) {
		AnswerQuestion answerQuestion = questionAnswerService.findById(estimateDto.getQuestionId());
		questionAnswerService.estimate(estimateDto.getQuestionId(), estimateDto.getEvaluation());
		Questionnaire questionnaire = questionnaireRepository.findByIdFullRelationsMapped(
				estimateDto.getQuestionnaireId());

		AnswerTopic updatedAnswerTopic = questionnaire.getTopics().stream()
				.filter(answerTopic -> answerTopic.getId() == answerQuestion.getAnswerTopicId())
				.findFirst().orElseThrow(AnswerTopicNotFoundException::new);
		estimateAndSaveAnswerTopic(updatedAnswerTopic);
		estimateAndSaveQuestionnaire(questionnaire);
	}

	public int estimate(List<Integer> estimates) {
		long noneProcent = estimates.stream()
			.filter(integer -> integer == NONE_ESTIMATE)
			.count() * (100 / estimates.size());
		if (noneProcent >= 15) {
			return NONE_ESTIMATE;
		}

		long intermediaCount = estimates.stream()
			.filter(integer -> integer == INTERN_ESTIMATE)
			.count() * (100 / estimates.size());
		if (intermediaCount >= 25) {
			return INTERN_ESTIMATE;
		}

		long noviceCount = estimates.stream()
			.filter(integer -> integer == NOTICE_ESTIMATE)
			.count() * (100 / estimates.size());
		if (noviceCount >= 50) {
			return NOTICE_ESTIMATE;
		}

		return ADVANCED_ESTIMATE;
	}

	private void estimateAndSaveQuestionnaire(Questionnaire questionnaire) {
		List<Integer> estimates = questionnaire.getTopics().stream()
				.filter(answerTopic -> Objects.nonNull(answerTopic.getEvaluation()))
				.mapToInt(AnswerTopic::getEvaluation).boxed()
				.toList();
		if (estimates.isEmpty()) {
			throw new EstimateException("Empty estimates for questionnaire=%s".formatted(questionnaire.getId()));
		}
		questionnaire.setEvaluation(estimate(estimates));
		questionnaireRepository.update(questionnaire);
	}

	private void estimateAndSaveAnswerTopic(AnswerTopic updatedAnswerTopic) {
		List<Integer> topicEstimates = updatedAnswerTopic.getQuestions().stream()
				.filter(aq -> Objects.nonNull(aq.getEvaluation()))
				.mapToInt(AnswerQuestion::getEvaluation).boxed()
				.toList();
		if (topicEstimates.isEmpty()) {
			throw new EstimateException("Empty estimates for answer topic=%s".formatted(updatedAnswerTopic.getName()));
		}
		updatedAnswerTopic.setEvaluation(estimate(topicEstimates));
		answerTopicRepository.update(updatedAnswerTopic);
	}
}
