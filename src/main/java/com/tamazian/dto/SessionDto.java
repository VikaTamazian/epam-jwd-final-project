package com.tamazian.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SessionDto {
    Long id;
    String email;

}
