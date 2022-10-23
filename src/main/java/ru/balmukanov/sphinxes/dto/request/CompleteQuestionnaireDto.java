package ru.balmukanov.sphinxes.dto.request;

import lombok.Data;

@Data
public class CompleteQuestionnaireDto {
    private long id;
    private String strengths;
    private String weaknesses;
}
