package org.example.planner.domain.planner.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PlannerRequestDto {
    @Size(max = 20, message = "제목의 글자수는 20보다 작아야 됩니다.")
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String contents;
}
