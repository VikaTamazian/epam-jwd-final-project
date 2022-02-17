package com.tamazian.service;

import com.tamazian.dao.UserDao;
import com.tamazian.dto.CreateUserDto;
import com.tamazian.entity.User;
import com.tamazian.exception.ValidationException;
import com.tamazian.mapper.CreateUserMapper;
import com.tamazian.validation.CreateUserValidator;
import com.tamazian.validation.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    public String create(CreateUserDto userDto) {
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(userDto);
        userDao.save(userEntity);

        return String.format("User save with id %d", userEntity.getId());
    }


    public List<UserDto> findAll() {
        return userDao.findAll()
                .stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .description(
                                """
                                         %s - %s - %s
                                        """.formatted(user.getFirstName(),
                                        user.getLastName(),
                                        user.getEmail()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
