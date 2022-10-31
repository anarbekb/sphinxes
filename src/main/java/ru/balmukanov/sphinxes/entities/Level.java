package ru.balmukanov.sphinxes.entities;

import java.util.List;

public enum Level {
    TRAINEE,
    J1,
    J2,
    J3,
    M1,
    M2,
    M3;

    public static List<Level> adjacentLevels(Level level) {
        return switch (level) {
            case TRAINEE -> List.of(TRAINEE, J1);
            case J1 -> List.of(TRAINEE, J1, J2);
            case J2 -> List.of(TRAINEE, J1, J2, J3);
            case J3 -> List.of(TRAINEE, J1, J2, J3, M1);
            case M1 -> List.of(TRAINEE, J1, J2, J3, M1, M2);
            case M2, M3 -> List.of(TRAINEE, J1, J2, J3, M1, M2, M3);
        };
    }
}
