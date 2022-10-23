package ru.balmukanov.sphinxes.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class AnswerTopicDto {
    private String name;
    private Integer evaluation;
    private List<AnswerQuestionDto> questions;
}
