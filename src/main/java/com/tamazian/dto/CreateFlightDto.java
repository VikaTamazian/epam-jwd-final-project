package com.tamazian.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateFlightDto {
    String name;
    String date;
    String aircraftId;
    String toAirportId;
    String fromAirportId;
    Integer captainId;
    Integer firstOfficer;
    Integer chiefStewardId;
    Integer firstStewardId;
    Integer secondStewardId;
}
