package ru.balmukanov.sphinxes.services.estimate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class EstimateMethodPercentTest {

    @ParameterizedTest
    @MethodSource("provideArguments")
    void estimate_test(List<Integer> sources, int expected) {
        var estimateService = new EstimateMethodPercent();
        Assertions.assertEquals(expected, estimateService.estimate(sources));
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(List.of(4, 4, 1, 2), 1),
                Arguments.of(List.of(1, 1, 4, 4), 1),
                Arguments.of(List.of(1, 1, 2, 2), 1),
                Arguments.of(List.of(1, 2, 2, 3, 4), 1),
                Arguments.of(List.of(1, 2, 3, 4), 1),
                Arguments.of(List.of(2, 3, 3, 3), 2),
                Arguments.of(List.of(2, 4, 4, 4), 2),
                Arguments.of(List.of(1, 2, 2, 2, 3, 3, 3, 3, 4, 4), 2),
                Arguments.of(List.of(2, 3, 3, 3, 3), 3),
                Arguments.of(List.of(2, 4, 4, 4, 4), 4),
                Arguments.of(List.of(3, 4), 3),
                Arguments.of(List.of(3, 4, 4), 4),
                Arguments.of(List.of(3, 4, 4, 4), 4),
                Arguments.of(List.of(3, 4, 4, 4, 4), 4)
        );
    }
}
