package org.example.planner.service;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.PlannerResponseDto;
import org.example.planner.entity.Planner;
import org.example.planner.entity.User;
import org.example.planner.repository.PlannerRepository;
import org.example.planner.repository.UserRepository;
import org.example.planner.utils.SessionUtils;
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
    private final SessionUtils sessionUtils;

    // 일정 저장 메서드
    @Transactional
    public PlannerResponseDto savePlanner(String title, String contents, String username) {
        if(!sessionUtils.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 해주세요");
        }

        User findUser = userRepository.findUserByUsernameOrElseThrow(username);

        Planner savedPlanner = plannerRepository.save(new Planner(title, contents));
        savedPlanner.setUser(findUser);

        return new PlannerResponseDto(savedPlanner.getTitle(),
                savedPlanner.getContents(),
                savedPlanner.getUsername());
    }

    // 일정 전체 조회 메서드
    public List<PlannerResponseDto> findAll() {
        if(!sessionUtils.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 해주세요");
        }

        return plannerRepository.findAll()
                .stream()
                .map(PlannerResponseDto::toDto)
                .toList();
    }

    // 일정 단건 조회 메서드
    public PlannerResponseDto findById(Long id) {
        if(!sessionUtils.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 해주세요");
        }

        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        return new PlannerResponseDto(findPlanner.getTitle(),
                findPlanner.getContents(),
                findPlanner.getUsername());
    }

    // 일정 수정 메서드
    @Transactional
    public PlannerResponseDto updateById(Long id, String title, String contents) {
        if(!sessionUtils.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 해주세요");
        }

        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);
        findPlanner.update(title, contents);

        return new PlannerResponseDto(title, contents, findPlanner.getUsername());
    }

    // 일정 삭제 메서드
    @Transactional
    public void deleteById(Long id) {
        if(!sessionUtils.isLoggedIn()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인을 해주세요");
        }

        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        plannerRepository.delete(findPlanner);
    }
}