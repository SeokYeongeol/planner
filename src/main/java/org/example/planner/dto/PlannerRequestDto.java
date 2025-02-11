package org.example.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlannerRequestDto {
    @NotEmpty(message = "제목을 입력해주세요.")
    private final String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private final String contents;
}
