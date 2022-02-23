package com.tamazian.validation;

import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.enam.Title;
import com.tamazian.util.LocalDateFormatter;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    @Override
    public ValidationResult isValid(CreateUserDto entity) {
        ValidationResult validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(entity.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is incorrect"));
        }

        if (entity.getTitle() == null || Title.find(entity.getTitle()).isEmpty()) {
            validationResult.add(Error.of("invalid.title", "Title is unselected"));
        }

        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}
