package ru.balmukanov.sphinxes.entities;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Question implements Serializable {
    private long id;
    private String point;
    private String answer;
    private long topicId;
    private String links;
    private String subject;
    private Level level;
}