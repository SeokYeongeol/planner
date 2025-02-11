package org.example.planner.service;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.PlannerResponseDto;
import org.example.planner.entity.Planner;
import org.example.planner.repository.PlannerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlannerService {
    private final PlannerRepository plannerRepository;

    @Transactional
    public PlannerResponseDto savePlanner(String title, String contents) {
        Planner savedPlanner = plannerRepository.save(new Planner(title, contents));

        return new PlannerResponseDto(savedPlanner.getTitle(),
                savedPlanner.getContents(),
                savedPlanner.getUser().getUsername());
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
                findPlanner.getUser().getUsername());
    }

    @Transactional
    public PlannerResponseDto updateById(Long id, String title, String contents) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);
        findPlanner.update(title, contents);

        return new PlannerResponseDto(title, contents, findPlanner.getUser().getUsername());
    }

    @Transactional
    public void deleteById(Long id) {
        Planner findPlanner = plannerRepository.findByIdOrElseThrow(id);

        plannerRepository.delete(findPlanner);
    }
}