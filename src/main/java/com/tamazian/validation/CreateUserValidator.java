package com.tamazian.validation;

import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.Position;
import com.tamazian.util.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto>{

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto entity) {
        ValidationResult validationResult = new ValidationResult();
        if(!LocalDateFormatter.isValid(entity.getBirthday())){
            validationResult.add(Error.of("invalid.position", "Birthday is incorrect"));
        }

        if(Position.find(entity.getPosition()).isEmpty()){
            validationResult.add(Error.of("invalid.position", "Position is unavailable"));
        }

        return validationResult;
    }

    public static CreateUserValidator getInstance(){
        return INSTANCE;
    }
}
