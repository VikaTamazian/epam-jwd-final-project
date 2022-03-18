package com.tamazian.mapper;

import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.enam.Title;
import com.tamazian.entity.User;
import com.tamazian.util.LocalDateFormatter;

public class CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();
    private static final String IMAGE_FOLDER = "\\users\\";

    @Override
    public User mapFrom(CreateUserDto entity) {
        return User.builder()
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .image(IMAGE_FOLDER + entity.getImage().getSubmittedFileName())
                .title(Title.valueOf(entity.getTitle()))
                .birthday(LocalDateFormatter.format(entity.getBirthday()))
                .build();
    }

    public static CreateUserMapper getInstance(){
        return INSTANCE;
    }
}
