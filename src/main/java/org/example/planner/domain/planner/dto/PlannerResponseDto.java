package org.example.planner.domain.planner.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.planner.domain.planner.entity.Planner;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class PlannerResponseDto {
    private final Long id;
    private final Long userId;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    // 싱글톤 패턴으로 쉽게 접근을 위한 메서드
    public static PlannerResponseDto toDto(Planner planner) {
        return new PlannerResponseDto(
                planner.getId(),
                planner.getUser().getId(),
                planner.getTitle(),
                planner.getContents(),
                planner.getCreatedAt(),
                planner.getModifiedAt()
        );
    }
}
