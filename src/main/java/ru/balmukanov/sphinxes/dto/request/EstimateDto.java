package ru.balmukanov.sphinxes.dto.request;

import lombok.Data;

@Data
public class EstimateDto {
    private Integer evaluation;
    private long questionnaireId;
}
