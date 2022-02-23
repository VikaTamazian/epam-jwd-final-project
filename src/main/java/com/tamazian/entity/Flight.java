package com.tamazian.entity;

import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight extends Entity {
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
