package com.tamazian.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Position {
    ADMIN,
    TRAFFIC_CONTROLLER,
    CAPTAIN,
    PILOT,
    CHIEF_STEWARD,
    STEWARD;

    public static Optional<Position> find(String position){
        return Arrays.stream(values())
                .filter(it -> it.name().equals(position))
                .findFirst();
    }

}
