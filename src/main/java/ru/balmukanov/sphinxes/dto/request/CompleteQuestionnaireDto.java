package ru.balmukanov.sphinxes.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@Validated
public class CompleteQuestionnaireDto {
    @Min(1)
    private long id;
    @NotEmpty
    private String strengths;
    @NotEmpty
    private String weaknesses;
}
