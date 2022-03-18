package com.tamazian.dto;

import com.tamazian.entity.enam.Title;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String email;
    String firstName;
    String lastName;
    String image;
    Title title;
    LocalDate birthday;


}
