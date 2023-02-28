package ru.balmukanov.sphinxes.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuestionDto {
    @NotBlank
    private String point;
    @NotBlank
    private String answer;
    @Min(1)
    private long topicId;
    @NotBlank
    private String subject;
    @NotBlank
    private String level;
    private String links;
}
