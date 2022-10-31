package ru.balmukanov.sphinxes.dto.response;

import lombok.Data;

@Data
public class AnswerQuestionDto {
    private long id;
    private String point;
    private String answer;
    private String links;
    private Integer evaluation;
}
