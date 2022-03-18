package com.tamazian.validation;

public interface Validator<E> {
    ValidationResult isValid(E entity);
}
