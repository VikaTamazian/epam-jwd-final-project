package com.tamazian.dao.factory;

import com.tamazian.entity.Title;
import com.tamazian.entity.User;
import com.tamazian.exception.DaoException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {
    // private static final Logger LOGGER = LogManager.getLogger();

    public User createUser(ResultSet resultSet) throws DaoException {
        User user = new User();
        try {
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setImage(resultSet.getString("image"));
            user.setTitle(Title.valueOf(resultSet.getString("title")));
            user.setBirthday(resultSet.getObject("birthday", Date.class).toLocalDate());

        } catch (SQLException e) {
            //LOGGER.log(Level.ERROR, "Create user error. " + e.getMessage());
            throw new DaoException("Create user error. " + e.getMessage());
        }
        return user;
    }

}
