package ru.balmukanov.sphinxes.dto.response;

import java.util.List;
import lombok.Data;

@Data
public class AnswerTopicDto {
    private String name;
    private List<AnswerQuestionDto> questions;
}
