package com.tamazian.validation;

import com.tamazian.dto.CreateFlightDto;
import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.enam.Aircraft;
import com.tamazian.entity.enam.Airport;
import com.tamazian.entity.enam.Title;
import com.tamazian.util.LocalDateAndTimeFormatter;
import com.tamazian.util.LocalDateFormatter;

public class CreateFlightValidator implements Validator<CreateFlightDto> {

    private static final CreateFlightValidator INSTANCE = new CreateFlightValidator();

    @Override
    public ValidationResult isValid(CreateFlightDto entity) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateAndTimeFormatter.isValid(entity.getDate())) {
            validationResult.add(Error.of("invalid.date", "Date is incorrect"));
        }

        if (entity.getAircraftId() == null || Aircraft.find(entity.getAircraftId()).isEmpty()) {
            validationResult.add(Error.of("invalid.aircraft", "Aircraft is unselected"));
        }

        if (entity.getToAirportId() == null || Airport.find(entity.getToAirportId()).isEmpty()) {
            validationResult.add(Error.of("invalid.airport", "Airport is unselected"));
        }

        if (entity.getFromAirportId() == null || Airport.find(entity.getFromAirportId()).isEmpty()) {
            validationResult.add(Error.of("invalid.airport", "Airport is unselected"));
        }

        return validationResult;
    }

    public static CreateFlightValidator getInstance() {
        return INSTANCE;
    }
}
