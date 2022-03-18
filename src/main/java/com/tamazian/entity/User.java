package com.tamazian.entity;

import com.tamazian.entity.enam.Title;
import lombok.*;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends Entity {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String image;
    private Title title;
    private LocalDate birthday;

}
