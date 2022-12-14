package ru.balmukanov.sphinxes.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.dto.request.EstimateDto;
import ru.balmukanov.sphinxes.entities.AnswerQuestion;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EstimateServiceImpl implements EstimateService {
	private final static int NONE_ESTIMATE = 1;
	private final static int INTERN_ESTIMATE = 2;
	private final static int NOTICE_ESTIMATE = 3;
	private final static int ADVANCED_ESTIMATE = 4;
	private final QuestionnaireRepository questionnaireRepository;
	private final AnswerTopicRepository answerTopicRepository;
	private final QuestionService questionService;

	@Override
	@Transactional
	public void estimate(EstimateDto estimateDto) {
		questionService.estimate(estimateDto.getQuestionId(), estimateDto.getEvaluation());
		Questionnaire questionnaire = questionnaireRepository.findByIdWithTopicsAndQuestions(estimateDto.getQuestionnaireId());
		questionnaire.getTopics().forEach(answerTopic -> {
			List<Integer> estimates = answerTopic.getQuestions().stream()
				.filter(answerQuestion -> Objects.nonNull(answerQuestion.getEvaluation()))
				.mapToInt(AnswerQuestion::getEvaluation).boxed()
				.toList();
			if (!estimates.isEmpty()) {
				answerTopic.setEvaluation(estimate(estimates));
				answerTopicRepository.update(answerTopic);
			}
		});
		List<Integer> estimates = questionnaire.getTopics().stream()
			.filter(answerTopic -> Objects.nonNull(answerTopic.getEvaluation()))
			.mapToInt(AnswerTopic::getEvaluation).boxed()
			.toList();
		if (!estimates.isEmpty()) {
			questionnaire.setEvaluation(estimate(estimates));
			questionnaireRepository.update(questionnaire);
		}
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
}