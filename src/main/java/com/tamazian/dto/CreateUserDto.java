package com.tamazian.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String email;
    String password;
    String firstName;
    String lastName;
    String position;
    String birthday;
}
