package org.example.planner.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.planner.entity.Planner;

@Getter
@RequiredArgsConstructor
public class PlannerResponseDto {
    private final String title;
    private final String contents;
    private final String username;

    // 싱글톤 패턴으로 쉽게 접근을 위한 메서드
    public static PlannerResponseDto toDto(Planner planner) {
        return new PlannerResponseDto(planner.getTitle(), planner.getContents(), planner.getUsername());
    }
}
