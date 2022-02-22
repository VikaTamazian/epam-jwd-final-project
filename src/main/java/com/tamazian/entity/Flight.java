package com.tamazian.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    private Integer id;
    private String name;
    private LocalDateTime date;
    private Integer aircraftId;
    private Integer toAirportId;
    private Integer fromAirportId;
    private Integer captainId;
    private Integer firstOfficerId;
    private Integer chiefStewardId;
    private Integer firstStewardId;
    private Integer secondStewardId;

}
