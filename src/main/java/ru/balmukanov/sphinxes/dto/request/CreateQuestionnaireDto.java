package ru.balmukanov.sphinxes.dto.request;

import lombok.Data;

@Data
public class CreateQuestionnaireDto {
    private String candidateFullName;
    private String project;
    private String candidateLevel;
}
