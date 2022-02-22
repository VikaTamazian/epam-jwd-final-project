package com.tamazian.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String image;
    private Title title;
    private LocalDate birthday;

}
