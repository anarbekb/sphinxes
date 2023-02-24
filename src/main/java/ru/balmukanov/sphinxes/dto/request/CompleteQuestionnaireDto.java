package ru.balmukanov.sphinxes.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Validated
@RequiredArgsConstructor
public class CompleteQuestionnaireDto {
    @Min(1)
    private final long id;
    @NotBlank
    private final String strengths;
    @NotBlank
    private final String weaknesses;
}
