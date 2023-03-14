package ru.balmukanov.sphinxes.services.questionnaire;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Topic;
import ru.balmukanov.sphinxes.mappers.TopicMapper;
import ru.balmukanov.sphinxes.repository.AnswerTopicRepository;

@Service
@RequiredArgsConstructor
public class AnswerTopicServiceImpl implements AnswerTopicService {
    private final AnswerTopicRepository answerTopicRepository;
    private final TopicMapper topicMapper;

    @Override
    public AnswerTopic toAnswer(Topic topic, long questionnaireId) {
        AnswerTopic answerTopic = topicMapper.mapToAnswerTopic(topic);
        answerTopic.setQuestionnaireId(questionnaireId);
        answerTopicRepository.save(answerTopic);
        return answerTopic;
    }

    @Override
    @Transactional
    public void deleteUnused(long questionnaireId) {
        answerTopicRepository.deleteUnused(questionnaireId);
    }
}
