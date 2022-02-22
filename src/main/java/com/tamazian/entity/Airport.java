package com.tamazian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum Airport {
    AES(1, "Aalesund Norway"),  //Aalesund Norway
    AMS(2, "Amsterdam Netherlands"),  //Amsterdam Netherlands
    ANR(3, ""),  //Antwerp Belgium
    AKL(4, ""), //Auckland New Zealand
    BKK(5, ""), //Bangkok Thailand
    BCN(6, ""), //Barcelona Spain
    SXF(7, ""), //Berlin	Germany
    BTS(8, ""), //Bratislava	Slovakia
    CMN(9, ""), //Casablanca	Morocco
    DUB(10, ""), //Dublin	Ireland
    DXB(11, ""), //Dubai	United Arab Emirates
    HEL(12, ""), //Helsinki	Finland
    INN(13, ""), //Innsbruck	Austria
    JKT(14, ""), //Jakarta, Java	Indonesia
    KRK(15, ""), //Krakow	Poland
    MSQ(16, ""), //Minsk	Belarus
    KMI(17, ""), //Miyazaki Japan
    YUL(18, ""), //Montreal Canada
    OSR(19, ""), //Ostrava Czech Republic
    CDG(20, ""), //Paris France
    PRG(21, ""), //Prague	Czech Republic
    TFS(22, ""), //Tenerife Island Canary Islands
    WAW(23, ""); //Warsaw	Poland

    private final int id;
    private final String destination;

    public static Optional<Airport> find(String destination) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(destination))
                .findFirst();
    }

}
