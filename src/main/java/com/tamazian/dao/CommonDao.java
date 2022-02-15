package com.tamazian.dao;

import java.util.List;
import java.util.Optional;

public interface CommonDao<K, T> {

    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    void update(T entity);

    T save(T entity);

}
