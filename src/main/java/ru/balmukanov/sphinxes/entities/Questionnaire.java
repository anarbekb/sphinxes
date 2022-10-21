package ru.balmukanov.sphinxes.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Questionnaire implements Serializable {
    private long id;
    private Integer evaluation;
    private Instant createdDt;
    private String candidate;
    private String project;
    private QuestionnaireStatus status;
    private List<AnswerTopic> topics;
}
