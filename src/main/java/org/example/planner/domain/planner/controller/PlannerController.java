package org.example.planner.domain.planner.controller;

import lombok.RequiredArgsConstructor;
import org.example.planner.consts.Const;
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
    public ResponseEntity<PlannerResponseDto> savePlanner(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Validated @RequestBody PlannerRequestDto dto
    ) {
        PlannerResponseDto responseDto = plannerService.savePlanner(userId, dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 일정 전체 조회 API
    @GetMapping
    public ResponseEntity<List<PlannerResponseDto>> findAll(
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        return new ResponseEntity<>(plannerService.findAll(userId), HttpStatus.OK);
    }

    // 일정 단건 조회 API
    @GetMapping("/{id}")
    public ResponseEntity<PlannerResponseDto> findById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        return new ResponseEntity<>(plannerService.findById(id, userId), HttpStatus.OK);
    }

    // 일정 수정 API
    @PutMapping("/{id}")
    public ResponseEntity<PlannerResponseDto> updateById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId,
            @Validated @RequestBody PlannerRequestDto dto
    ) {
        PlannerResponseDto responseDto = plannerService.updateById(id, userId, dto.getTitle(), dto.getContents());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 일정 삭제 API
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id,
            @SessionAttribute(name = Const.LOGIN_USER) Long userId
    ) {
        plannerService.deleteById(id, userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}