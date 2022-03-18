package com.tamazian.validation;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
