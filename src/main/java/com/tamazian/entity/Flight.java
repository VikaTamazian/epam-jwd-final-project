package com.tamazian.entity;

import com.tamazian.entity.enam.Aircraft;
import com.tamazian.entity.enam.Airport;
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
    private Aircraft aircraftId;
    private Airport toAirportId;
    private Airport fromAirportId;
    private Integer captainId;
    private Integer firstOfficerId;
    private Integer chiefStewardId;
    private Integer firstStewardId;
    private Integer secondStewardId;

}
