package ru.balmukanov.sphinxes.entities;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerTopic implements Serializable {
    private long id;
    private String name;
    private Integer evaluation;
    private long questionnaireId;
    private List<AnswerQuestion> questions;
}
