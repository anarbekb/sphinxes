package ru.balmukanov.sphinxes.services;

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
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

@Service
@AllArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final FeedbackService feedbackService;
    private final QuestionnaireMapper questionnaireMapper;
    private final TopicService topicService;
    private final QuestionAnswerService questionAnswerService;

    @Override
    @Transactional
    public long create(CreateQuestionnaireDto request, User principal) {
        List<Topic> topics = topicService.findByLevels(Level.adjacentLevels(Level.valueOf(request.getCandidateLevel())));

        Questionnaire questionnaire = questionnaireMapper.mapFromRequest(request);
        questionnaire.setCreator(principal);
        questionnaireRepository.save(questionnaire);
        for (Topic topic : topics) {
            AnswerTopic answerTopic = topicService.toAnswer(topic, questionnaire.getId());
            questionAnswerService.toAnswerQuestionAndSave(topic.getQuestions(), answerTopic.getId());
        }

        return questionnaire.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionnaireDto getQuestionnaire(long id) {
        Questionnaire questionnaire = questionnaireRepository.findByIdFullRelationsMapped(id);
        return questionnaireMapper.mapToDto(questionnaire);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionnaireDto> findByUserWithFeedback(User principal) {
        return questionnaireRepository.findByUser(principal.getId()).stream()
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
        feedbackService.create(completeQuestionnaireDto);
	}
}
