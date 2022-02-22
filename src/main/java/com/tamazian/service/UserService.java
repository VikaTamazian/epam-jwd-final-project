package com.tamazian.service;

import com.tamazian.dao.UserDao;
import com.tamazian.dto.CreateUserDto;
import com.tamazian.dto.UserDto;
import com.tamazian.entity.User;
import com.tamazian.exception.ValidationException;
import com.tamazian.mapper.CreateUserMapper;
import com.tamazian.mapper.UserMapper;
import com.tamazian.validation.CreateUserValidator;
import com.tamazian.validation.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(userMapper::mapFrom);
    }

    @SneakyThrows
    public String create(CreateUserDto userDto) {
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        User userEntity = createUserMapper.mapFrom(userDto);
        imageService.upload(userEntity.getImage(),
                userDto.getImage().getInputStream());
        userDao.save(userEntity);

        return String.format("User save with id %d", userEntity.getId());
    }


    public List<UserDto> findAll() {
        return userDao.findAll()
                .stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .image(user.getImage())
                        .title(user.getTitle())
                        .birthday(user.getBirthday())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
