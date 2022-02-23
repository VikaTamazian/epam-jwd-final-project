package com.tamazian.dao.impl;

import com.tamazian.dao.UserDao;
import com.tamazian.entity.User;
import com.tamazian.entity.enam.Title;
import com.tamazian.exception.DaoException;
import com.tamazian.util.ConnectionManager;
import com.tamazian.util.PasswordHashGenerator;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final static UserDaoImpl INSTANCE = new UserDaoImpl();

    private static final String FIND_ALL_SQL = """
            SELECT id, email, password, firstName, lastName, image, title, birthday
            FROM users ORDER BY lastName
            """;
    private static final String SAVE_SQL = """
            INSERT INTO users (email, password, firstName, lastName, image,  title, birthday, registration_key)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?);
            """;
    public static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, email, password, firstName, lastName, image, title, birthday FROM users
            WHERE email =? AND password =?;
            """;
    private static final String CHANGE_USER_PASSWORD = """
            UPDATE users SET password = ? WHERE email = ?
            """;
    private static final String FIND_USER_BY_ID_SQL = FIND_ALL_SQL + " WHERE u.id = ?";
    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String UPDATE_SQL = "UPDATE users SET email=?, firstName=?, lastName=?, image=?, title=?, birthday=? WHERE id= ?";

    private static final String FIND_USER_BY_EMAIL_SQL = FIND_ALL_SQL + " WHERE u.email = ?";
    private static final String CHANGE_USER_TITLE_SQL = "UPDATE users SET title_id = ? WHERE id = ?";
    private static final String CHANGE_USER_IMAGE_SQL = "UPDATE users SET image = ? WHERE email = ?";


    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return INSTANCE;
    }


    @Override
    public List<User> findAll() throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(getBuild(resultSet));
            }
            return users;

        } catch (SQLException e) {
            throw new DaoException("Error with showing the list of users", e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) throws DaoException {

        User user = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID_SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getBuild(resultSet);
            }
        } catch (SQLException e) {
            //  LOGGER.log(Level.ERROR, "Error with find User by id .", e);
            throw new DaoException("Error with find User by id", e);
        }

        return Optional.ofNullable(user);
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        User user = null;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getBuild(resultSet);
            }
        } catch (SQLException e) {
            //  LOGGER.log(Level.ERROR, "Error with find User by id .", e);
            throw new DaoException("Error with find User by email", e);
        }
        return Optional.ofNullable(user);
    }


    @Override
    public boolean delete(Integer id) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Impossible to delete the user", e);
        }
    }

    @Override
    public void update(User entity) throws DaoException {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setObject(1, entity.getEmail());
            preparedStatement.setObject(2, entity.getFirstName());
            preparedStatement.setObject(3, entity.getLastName());
            preparedStatement.setObject(4, entity.getImage());
            preparedStatement.setObject(5, entity.getTitle());
            preparedStatement.setObject(6, entity.getBirthday());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Impossible to update", e);
        }
    }

    @Override
    public void save(User entity) throws DaoException {
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

        } catch (SQLException e) {
            throw new DaoException("Error with adding new User. ", e);

        }
    }

    @Override
    public boolean createNewUserWithKey(User entity, String password, String registrationKey) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, entity.getEmail());
            preparedStatement.setObject(2, PasswordHashGenerator.encodePassword(password));
            preparedStatement.setObject(3, entity.getFirstName());
            preparedStatement.setObject(4, entity.getLastName());
            preparedStatement.setObject(5, entity.getImage());
            preparedStatement.setObject(6, entity.getTitle().getId());
            preparedStatement.setObject(7, entity.getBirthday());
            preparedStatement.setObject(8, registrationKey);

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            entity.setId(generatedKeys.getInt(1));
            return true;

        } catch (SQLException e) {
            throw new DaoException("Error with adding new User. ", e);
        }

    }

    @Override
    public boolean createNewUserForAdmin(User user, String password) throws DaoException {
        return createNewUserWithKey(user, password, null);
    }


    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {

        User user = null;
        if (email != null) {
            try (Connection connection = ConnectionManager.get();
                 PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL_SQL)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    user = getBuild(resultSet);
                }
            } catch (SQLException e) {
                //  LOGGER.log(Level.ERROR, "Error with find User by email .", e);
                throw new DaoException("Impossible to find User by email .", e);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean changeUserTitleById(Integer id, Title title) throws DaoException {

        int rowsUpdate;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(CHANGE_USER_TITLE_SQL)) {
            statement.setInt(1, title.getId());
            statement.setInt(2, id);
            rowsUpdate = statement.executeUpdate();
        } catch (SQLException e) {
            //LOGGER.log(Level.ERROR, "Error with changing user role. ", e);
            throw new DaoException("Error with changing user role. ", e);
        }

        return rowsUpdate == 1;
    }

    @Override
    public void changePassword(String email, String password) throws DaoException {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(CHANGE_USER_PASSWORD)) {
            statement.setString(1, password);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            // LOGGER.log(Level.ERROR, "Error with changing password. ", e);
            throw new DaoException("Error with changing password. ", e);
        }
    }

    @Override
    public void changeUserImage(String email, String image) throws DaoException {

        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection.prepareStatement(CHANGE_USER_IMAGE_SQL)) {
            statement.setString(1, image);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            //LOGGER.log(Level.ERROR, "Error with changing image. ", e);
            throw new DaoException("Error with changing image. ", e);
        }
    }

    @Override
    public int getUserCount() throws DaoException {
        return rowCountByQuery(FIND_ALL_SQL);
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


//        private User buildUser(ResultSet resultSet) throws SQLException {
//        return new User(
//                resultSet.getObject("id", Integer.class),
//                resultSet.getObject("email", String.class),
//                resultSet.getObject("password", String.class),
//                resultSet.getObject("firstName", String.class),
//                resultSet.getObject("lastName", String.class),
//                resultSet.getObject("image", String.class),
//                Title.find(resultSet.getObject("title", String.class)).orElse(null),
//                resultSet.getObject("birthday", LocalDate.class)
//        );
//    }
}
