package ru.balmukanov.sphinxes.services;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.dto.request.CreateQuestionnaireDto;
import ru.balmukanov.sphinxes.dto.response.QuestionnaireDto;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Level;
import ru.balmukanov.sphinxes.entities.Questionnaire;
import ru.balmukanov.sphinxes.entities.QuestionnaireStatus;
import ru.balmukanov.sphinxes.entities.Topic;
import ru.balmukanov.sphinxes.exception.ClosedQuestionnaireException;
import ru.balmukanov.sphinxes.mappers.QuestionnaireMapper;
import ru.balmukanov.sphinxes.repository.QuestionnaireRepository;

@Service
@AllArgsConstructor
public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionnaireRepository questionnaireRepository;
    private final QuestionnaireMapper questionnaireMapper;
    private final TopicService topicService;
    private final QuestionService questionService;

    @Override
    @Transactional
    public long generateQuestionnaire(CreateQuestionnaireDto request) {
        List<Topic> topics = topicService.findByLevels(Level.adjacentLevels(Level.valueOf(request.getCandidateLevel())));

        Questionnaire questionnaire = questionnaireMapper.mapFromRequest(request);
        questionnaireRepository.save(questionnaire);
        for (Topic topic : topics) {
            AnswerTopic answerTopic = topicService.toAnswer(topic, questionnaire.getId());
            questionService.toAnswerQuestion(topic.getQuestions(), answerTopic.getId());
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
    public void checkAvailabilityForEdit(long questionnaireId) {
        Questionnaire questionnaire = questionnaireRepository.findById(questionnaireId);
        if (questionnaire.getStatus().equals(QuestionnaireStatus.CLOSED)) {
            throw new ClosedQuestionnaireException();
        }
    }
}
