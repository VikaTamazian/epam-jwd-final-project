package com.tamazian.mapper;

public interface Mapper<F, T> {

    T mapFrom(F entity);
}
