package com.tamazian.entity.enam;

import java.util.Arrays;
import java.util.Optional;

public enum Title {
    ADMIN(1),
    TRAFFIC_CONTROLLER(2),
    CAPTAIN(3),
    FIRST_OFFICER(4),
    CHIEF_STEWARD(5),
    STEWARD(6);

    private final int id;

    Title(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<Title> find(String title) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(title))
                .findFirst();
    }

}
