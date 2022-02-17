package com.tamazian.dao;

import com.tamazian.entity.Position;
import com.tamazian.entity.User;
import com.tamazian.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CommonDao<Long, User> {

    private final static UserDao INSTANCE = new UserDao();

    public static final String FIND_ALL_SQL = """
            SELECT *
            FROM users
            """;
    public static final String SAVE_SQL = """
            INSERT INTO users (email, password, firstName, lastName, position, birthday) 
            VALUES (?, ?, ?, ?, ?, ?);
            """;

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }


    @Override
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    @SneakyThrows
    public User save(User entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getEmail());
            preparedStatement.setObject(2, entity.getPassword());
            preparedStatement.setObject(3, entity.getFirstName());
            preparedStatement.setObject(4, entity.getLastName());
            preparedStatement.setObject(5, entity.getPosition().name());
            preparedStatement.setObject(6, entity.getBirthday());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getObject("id", Long.class));

            return entity;
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getObject("id", Long.class),
                resultSet.getObject("email", String.class),
                resultSet.getObject("password", String.class),
                resultSet.getObject("firstName", String.class),
                resultSet.getObject("lastName", String.class),
                Position.valueOf(resultSet.getObject("role", String.class)),
                resultSet.getObject("birthday", LocalDate.class)
        );
    }


}
