package com.tamazian.mapper;

import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.Position;
import com.tamazian.entity.User;
import com.tamazian.util.LocalDateFormatter;

import java.time.LocalDate;

public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(CreateUserDto entity) {
        return User.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .position(Position.valueOf(entity.getPosition()))
                .birthday(LocalDateFormatter.format(entity.getBirthday()))
                .build();
    }

    public static CreateUserMapper getInstance(){
        return INSTANCE;
    }
}
