package com.tamazian.mapper;

import com.tamazian.dto.UserDto;
import com.tamazian.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<User, UserDto>{

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapFrom(User entity) {
        return UserDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .image(entity.getImage())
                .title(entity.getTitle())
                .birthday(entity.getBirthday())
                .build();
    }

    public static UserMapper getInstance(){
        return INSTANCE;
    }
}
