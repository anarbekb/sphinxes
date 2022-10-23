package ru.balmukanov.sphinxes.services;

import ru.balmukanov.sphinxes.dto.request.EstimateDto;

import java.util.List;

public interface EstimateService {
	void estimate(EstimateDto estimateDto);

	int estimate(List<Integer> estimates);
}