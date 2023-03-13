package ru.balmukanov.sphinxes.dto.response;

import lombok.Data;

@Data
public class QuestionDto {
    private long id;
    private String point;
    private String level;
    private String answer;
    private String links;
}
