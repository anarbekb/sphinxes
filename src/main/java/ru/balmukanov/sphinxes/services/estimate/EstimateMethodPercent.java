package ru.balmukanov.sphinxes.services.estimate;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstimateMethodPercent implements EstimateMethod {
    private final static int NONE_ESTIMATE = 1;
    private final static int INTERN_ESTIMATE = 2;
    private final static int NOTICE_ESTIMATE = 3;
    private final static int ADVANCED_ESTIMATE = 4;

    @Override
    public int estimate(List<Integer> estimates) {
        long nonePercent = estimates.stream()
                .filter(integer -> integer == NONE_ESTIMATE)
                .count() * (100 / estimates.size());
        if (nonePercent >= 15) {
            return NONE_ESTIMATE;
        }

        long intermediaCount = estimates.stream()
                .filter(integer -> integer == INTERN_ESTIMATE)
                .count() * (100 / estimates.size());
        if (intermediaCount >= 25) {
            return INTERN_ESTIMATE;
        }

        long noviceCount = estimates.stream()
                .filter(integer -> integer == NOTICE_ESTIMATE)
                .count() * (100 / estimates.size());
        if (noviceCount >= 50) {
            return NOTICE_ESTIMATE;
        }

        return ADVANCED_ESTIMATE;
    }
}
