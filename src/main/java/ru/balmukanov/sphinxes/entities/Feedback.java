package ru.balmukanov.sphinxes.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Feedback implements Serializable {
	private long id;
	private String needImprove;
	private String weaknesses;
	private String strengths;
	private long questionnaireId;
}