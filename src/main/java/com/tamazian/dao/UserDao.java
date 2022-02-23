package com.tamazian.dao;

import com.tamazian.entity.User;
import com.tamazian.entity.enam.Title;
import com.tamazian.exception.DaoException;

import java.util.Optional;

public interface UserDao extends CommonDao<Integer, User>{

    boolean createNewUserForAdmin(User user, String password) throws DaoException;

    boolean createNewUserWithKey(User user, String password, String registrationKey) throws DaoException;

    Optional<User> findByEmailAndPassword(String email, String password) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    boolean changeUserTitleById(Integer id, Title title)throws DaoException;

    void changePassword(String email, String password) throws DaoException;

    void changeUserImage(String email, String image) throws DaoException;

    int getUserCount() throws DaoException;


}
