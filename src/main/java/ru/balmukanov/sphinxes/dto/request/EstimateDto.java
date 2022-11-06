package ru.balmukanov.sphinxes.dto.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Validated
public class EstimateDto {
    @Min(1)
    @Max(4)
    private Integer evaluation;
    @Min(1)
    private long questionnaireId;
    @Min(1)
    private long questionId;
}
