package ru.balmukanov.sphinxes.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class CreateQuestionnaireDto {
    @NotEmpty
    private String candidateFullName;
    @NotEmpty
    private String project;
    @NotEmpty
    private String candidateLevel;
}
