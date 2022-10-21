package ru.balmukanov.sphinxes.entities;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Topic implements Serializable {
    private long id;
    private String name;
    private List<Question> questions;
}
