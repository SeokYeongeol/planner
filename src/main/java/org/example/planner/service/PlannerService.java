package org.example.planner.service;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.PlannerResponseDto;
import org.example.planner.entity.Planner;
import org.example.planner.entity.User;
import org.example.planner.repository.PlannerRepository;
import org.example.planner.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlannerResponseDto savePlanner(String title, String contents, String username) {
        User findUser = userRepository.findUserByUsernameOrElseThrow(username);

        Planner savedPlanner = plannerRepository.save(new Planner(title, contents));
        savedPlanner.setUser(findUser);

        return new PlannerResponseDto(savedPlanner.getTitle(),
                savedPlanner.getContents(),
                savedPlanner.getUsername());
    }

    public List<PlannerResponseDto> findAll() {
        return plannerRepository.findAll()
                .stream()
                .map(PlannerResponseDto::toDto)
                .toList();
    }

    public PlannerResponseDto findById(Long id) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        return new PlannerResponseDto(findPlanner.getTitle(),
                findPlanner.getContents(),
                findPlanner.getUsername());
    }

    @Transactional
    public PlannerResponseDto updateById(Long id, String title, String contents) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);
        findPlanner.update(title, contents);

        return new PlannerResponseDto(title, contents, findPlanner.getUsername());
    }

    @Transactional
    public void deleteById(Long id) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        plannerRepository.delete(findPlanner);
    }
}