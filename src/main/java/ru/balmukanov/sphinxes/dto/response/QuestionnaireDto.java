package ru.balmukanov.sphinxes.dto.response;

import java.util.List;

public record QuestionnaireDto(
        long id,
        List<AnswerTopicDto> topics,
        String candidate,
        String project,
        String createdDt,
        String status,
        Integer evaluation,
        FeedbackDto feedback) {
}
