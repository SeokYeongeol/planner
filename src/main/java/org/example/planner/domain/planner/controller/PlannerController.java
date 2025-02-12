package org.example.planner.domain.planner.controller;

import lombok.RequiredArgsConstructor;
import org.example.planner.domain.planner.dto.PlannerRequestDto;
import org.example.planner.domain.planner.dto.PlannerResponseDto;
import org.example.planner.domain.planner.service.PlannerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/planners")
public class PlannerController {
    private final PlannerService plannerService;

    // 일정 생성 API
    @PostMapping
    public ResponseEntity<PlannerResponseDto> savePlanner(@Validated @RequestBody PlannerRequestDto dto) {
        PlannerResponseDto responseDto = plannerService.savePlanner(dto.getTitle(), dto.getContents(), dto.getUsername());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 일정 전체 조회 API
    @GetMapping
    public ResponseEntity<List<PlannerResponseDto>> findAll(@RequestParam String username) {
        return new ResponseEntity<>(plannerService.findAll(username), HttpStatus.OK);
    }

    // 일정 단건 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<PlannerResponseDto> findById(
            @PathVariable Long id,
            @RequestParam String username
    ) {
        return new ResponseEntity<>(plannerService.findById(id, username), HttpStatus.OK);
    }

    // 일정 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<PlannerResponseDto> updateById(
            @PathVariable Long id,
            @Validated @RequestBody PlannerRequestDto dto
    ) {
        PlannerResponseDto responseDto = plannerService.updateById(id, dto.getTitle(), dto.getContents(), dto.getUsername());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 일정 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @RequestParam String username
    ) {
        plannerService.deleteById(id, username);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}