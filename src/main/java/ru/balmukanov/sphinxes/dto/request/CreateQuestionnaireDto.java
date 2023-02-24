package ru.balmukanov.sphinxes.dto.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionnaireDto {
    @NotBlank
    private String candidateFullName;
    @NotBlank
    private String project;
    @NotBlank
    private String candidateLevel;
}
