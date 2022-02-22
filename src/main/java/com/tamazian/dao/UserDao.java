package com.tamazian.dao;

import com.tamazian.entity.Title;
import com.tamazian.entity.User;
import com.tamazian.util.ConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CommonDao<Integer, User> {

    private final static UserDao INSTANCE = new UserDao();

    private static final String FIND_ALL_SQL = """
            SELECT *
            FROM users
            """;
    private static final String SAVE_SQL = """
            INSERT INTO users (email, password, firstName, lastName, image,  title, birthday) 
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    public static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, email, password, firstName, lastName, image, title, birthday FROM users
            WHERE email =? AND password =?;
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
    public Optional<User> findById(Integer id) {
        return Optional.empty();
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if(resultSet.next()){
                user = getBuild(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @Override
    public boolean delete(Integer id) {
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
            preparedStatement.setObject(5, entity.getImage());
            preparedStatement.setObject(6, entity.getTitle().getId());
            preparedStatement.setObject(7, entity.getBirthday());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getInt(1));

            return entity;
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getObject("id", Integer.class),
                resultSet.getObject("email", String.class),
                resultSet.getObject("password", String.class),
                resultSet.getObject("firstName", String.class),
                resultSet.getObject("lastName", String.class),
                resultSet.getObject("image", String.class),
                Title.find(resultSet.getObject("title", String.class)).orElse(null),
                resultSet.getObject("birthday", LocalDate.class)
        );
    }

    private User getBuild(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getObject("id", Integer.class))
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("password", String.class))
                .firstName(resultSet.getObject("firstName", String.class))
                .lastName(resultSet.getObject("lastName", String.class))
                .image(resultSet.getObject("image", String.class))
                .title(Title.find(resultSet.getObject("title", String.class)).orElse(null))
                //   .title(Title.valueOf(resultSet.getObject("title", String.class))
                .birthday(resultSet.getObject("birthday", Date.class).toLocalDate())
                .build();
    }


}
