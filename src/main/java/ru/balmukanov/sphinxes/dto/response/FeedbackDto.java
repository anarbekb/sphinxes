package ru.balmukanov.sphinxes.dto.response;

public record FeedbackDto(
	long id,
	String needImprove,
	String weaknesses,
	String strengths) {
}