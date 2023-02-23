package ru.balmukanov.sphinxes.services;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.dto.request.CompleteQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;
import ru.balmukanov.sphinxes.entities.*;
import ru.balmukanov.sphinxes.exception.ClosedQuestionnaireException;
import ru.balmukanov.sphinxes.mappers.QuestionnaireMapper;
import ru.balmukanov.sphinxes.repository.AnswerQuestionRepository;
import ru.balmukanov.sphinxes.repository.FeedbackRepository;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

@Service
@AllArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final AnswerQuestionRepository answerQuestionRepository;
    private final FeedbackRepository feedbackRepository;
    private final QuestionnaireMapper questionnaireMapper;
    private final TopicService topicService;
    private final QuestionAnswerService questionAnswerService;

    @Override
    @Transactional
    public long generateQuestionnaire(CreateQuestionnaireDto request) {
        List<Topic> topics = topicService.findByLevels(Level.adjacentLevels(Level.valueOf(request.getCandidateLevel())));

        Questionnaire questionnaire = questionnaireMapper.mapFromRequest(request);
        questionnaireRepository.save(questionnaire);
        for (Topic topic : topics) {
            AnswerTopic answerTopic = topicService.toAnswer(topic, questionnaire.getId());
            questionAnswerService.toAnswerQuestion(topic.getQuestions(), answerTopic.getId());
        }

        return questionnaire.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionnaireDto getQuestionnaire(long id) {
        Questionnaire questionnaire = questionnaireRepository.findByIdWithTopicsAndQuestions(id);
        return questionnaireMapper.mapToDto(questionnaire);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionnaireDto> findAllWithFeedback() {
        return questionnaireRepository.findAllWithFeedback().stream()
            .map(questionnaireMapper::mapToDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public void checkAvailabilityForEdit(long questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId);
        if (questionnaire.getStatus().equals(QuestionnaireStatus.CLOSED)) {
            throw new ClosedQuestionnaireException();
        }
    }

	@Override
    @Transactional
	public void completeQuestionnaire(CompleteQuestionnaireDto completeQuestionnaireDto) {
        Questionnaire questionnaire = questionnaireRepository.findById(completeQuestionnaireDto.getId());
        questionnaire.setStatus(QuestionnaireStatus.CLOSED);
        questionnaireRepository.update(questionnaire);

        List<String> improves = new ArrayList<>();
        answerQuestionRepository.findFailed(completeQuestionnaireDto.getId()).forEach(answerQuestion -> {
            String improve = answerQuestion.getSubject();
            if (answerQuestion.getLinks() != null) {
                improve = improve + ": " + answerQuestion.getLinks();
            }
            improves.add(improve);
        });

        Feedback feedback = new Feedback();
        feedback.setNeedImprove(String.join(" , ", improves));
        feedback.setWeaknesses(completeQuestionnaireDto.getWeaknesses());
        feedback.setStrengths(completeQuestionnaireDto.getStrengths());
        feedback.setQuestionnaireId(completeQuestionnaireDto.getId());
        feedbackRepository.save(feedback);
	}
}
