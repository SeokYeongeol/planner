package org.example.planner.domain.planner.service;

import lombok.RequiredArgsConstructor;
import org.example.planner.domain.planner.dto.PlannerResponseDto;
import org.example.planner.domain.planner.entity.Planner;
import org.example.planner.domain.planner.repository.PlannerRepository;
import org.example.planner.domain.user.entity.User;
import org.example.planner.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;

    // 일정 저장 메서드
    @Transactional
    public PlannerResponseDto savePlanner(Long userId, String title, String contents) {
        User user = User.fromUserId(userId);
        Planner savedPlanner = plannerRepository.save(new Planner(user, title, contents));

        return new PlannerResponseDto(savedPlanner.getId(),
                savedPlanner.getUserId(),
                savedPlanner.getTitle(),
                savedPlanner.getContents(),
                savedPlanner.getCreatedAt(),
                savedPlanner.getModifiedAt());
    }

    // 일정 전체 조회 메서드 (특정 이름 검색)
    public List<PlannerResponseDto> findAll(Long userId) {
        // 유저 존재 여부 확인
        if(!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디의 사용자가 없습니다.");
        }

        return plannerRepository.findAllByUser_Id(userId)
                .stream()
                .map(PlannerResponseDto::toDto)
                .toList();
    }

    // 일정 단건 조회 메서드
    public PlannerResponseDto findById(Long id, Long userId) {
        // 유저 존재 여부 확인
        if(!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디의 사용자가 없습니다.");
        }

        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        return new PlannerResponseDto(findPlanner.getId(),
                findPlanner.getUserId(),
                findPlanner.getTitle(),
                findPlanner.getContents(),
                findPlanner.getCreatedAt(),
                findPlanner.getModifiedAt());
    }

    // 일정 수정 메서드
    @Transactional
    public PlannerResponseDto updateById(Long id, Long userId, String title, String contents) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        // 유저 존재 여부 확인
        if(!userId.equals(findPlanner.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "본인이 작성한 일정만 수정이 가능합니다.");
        }

        findPlanner.update(title, contents);

        return new PlannerResponseDto(findPlanner.getId(),
                findPlanner.getUserId(),
                title,
                contents,
                findPlanner.getCreatedAt(),
                findPlanner.getModifiedAt());
    }

    // 일정 삭제 메서드
    @Transactional
    public void deleteById(Long id, Long userId) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        if(!userId.equals(findPlanner.getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "본인이 작성한 일정만 삭제가 가능합니다.");
        }

        plannerRepository.delete(findPlanner);
    }
}