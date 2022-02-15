package com.tamazian.service;

import com.tamazian.dao.UserDao;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();

    private UserService() {
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
                                                user.getLastname(),
                                                user.getEmail()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
