package com.tamazian.mapper;

import com.tamazian.dto.CreateFlightDto;
import com.tamazian.entity.Flight;
import com.tamazian.entity.enam.Aircraft;
import com.tamazian.entity.enam.Airport;
import com.tamazian.util.LocalDateAndTimeFormatter;

public class CreateFlightMapper implements Mapper<CreateFlightDto, Flight> {
    private static final CreateFlightMapper INSTANCE = new CreateFlightMapper();

    @Override
    public Flight mapFrom(CreateFlightDto entity) {
        return Flight.builder()
                .name(entity.getName())
                .date(LocalDateAndTimeFormatter.format(entity.getDate()))
                .aircraftId(Aircraft.valueOf(entity.getAircraftId()))
                .toAirportId(Airport.valueOf(entity.getToAirportId()))
                .fromAirportId(Airport.valueOf(entity.getFromAirportId()))
                .captainId(entity.getCaptainId())
                .firstOfficerId(entity.getFirstOfficer())
                .chiefStewardId(entity.getChiefStewardId())
                .firstStewardId(entity.getFirstStewardId())
                .secondStewardId(entity.getSecondStewardId())
                .build();
    }

    public static CreateFlightMapper getInstance() {
        return INSTANCE;
    }

}
