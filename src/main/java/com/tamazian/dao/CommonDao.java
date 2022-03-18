package com.tamazian.dao;

import com.tamazian.entity.Entity;
import com.tamazian.exception.DaoException;
import com.tamazian.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CommonDao<K, T extends Entity> {

    int PAGE_SIZE = 10;

    List<T> findAll() throws DaoException;

    Optional<T> findById(K id) throws DaoException;

    boolean delete(K id) throws DaoException;

    void update(T entity) throws DaoException;

    void save(T entity) throws DaoException;

    default String buildPageableQuery(String mainQuery, int pageNumber) {
        int limit = PAGE_SIZE;
        int offset = (limit * pageNumber) - limit;
        StringBuilder queryBuilder = new StringBuilder(mainQuery);
        queryBuilder.append(" LIMIT ");
        if (offset > 0) {
            queryBuilder.append(offset).append(", ");
        }
        queryBuilder.append(limit);
        return queryBuilder.toString();
    }

    default int rowCountByQuery(String sourceQuery) throws DaoException {
        int result = 0;
        try (Connection connection = ConnectionManager.get();
             PreparedStatement statement = connection
                     .prepareStatement("SELECT COUNT(*) FROM (" + sourceQuery + ") as tbl")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            //  LOGGER.log(Level.ERROR, "Can't count rows", e);
            throw new DaoException("Can't count rows", e);
        }
        return result;
    }

}
