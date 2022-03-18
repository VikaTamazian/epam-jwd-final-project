package com.tamazian.entity.enam;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Aircraft {
    INTEGER_JET(1),
    SPRING_BOOT_AIRBUS(2),
    HIBERNATE_EMBRAER(3),
    MOCKITO_BOMBARDIER(4),
    TOMCAT_DOUGLAS(5),
    LOCKHEED_ORACLE(6),
    SERVLET_CESSNA(7),
    MVC_CONCORDE(8),
    JDBC_FALCON(9),
    MAVEN_AN(10),
    GRADLE_TU(11);


    private final int id;

    public static Optional<Aircraft> find(String plain) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(plain))
                .findFirst();
    }

}
