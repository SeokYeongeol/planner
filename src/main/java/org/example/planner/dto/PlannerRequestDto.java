package org.example.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PlannerRequestDto {
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String contents;
}
