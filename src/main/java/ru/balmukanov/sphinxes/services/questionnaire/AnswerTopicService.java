package ru.balmukanov.sphinxes.services.questionnaire;

import ru.balmukanov.sphinxes.entities.AnswerTopic;
import ru.balmukanov.sphinxes.entities.Topic;

public interface AnswerTopicService {
    AnswerTopic toAnswer(Topic topic, long questionnaireId);
}
