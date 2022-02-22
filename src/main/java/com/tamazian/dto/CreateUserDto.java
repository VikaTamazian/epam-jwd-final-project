package com.tamazian.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {
    String email;
    String password;
    String firstName;
    String lastName;
    Part image;
    String title;
    String birthday;
}
