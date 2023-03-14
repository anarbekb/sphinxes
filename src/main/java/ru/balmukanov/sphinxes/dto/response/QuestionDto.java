package ru.balmukanov.sphinxes.dto.response;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class QuestionDto {
    @Min(1)
    private long id;
    @NotBlank
    private String point;
    @NotBlank
    private String level;
    @NotBlank
    private String answer;
    private String links;
    @NotBlank
    private String subject;
    @Min(1)
    private long topicId;
}
