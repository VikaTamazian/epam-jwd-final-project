package com.tamazian.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SessionDto {
    Long id;
    String email;

}
