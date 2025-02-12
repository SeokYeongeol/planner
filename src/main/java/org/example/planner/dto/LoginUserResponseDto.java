package org.example.planner.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginUserResponseDto {
    private final Long id;
    private final String username;
    private final String email;
}