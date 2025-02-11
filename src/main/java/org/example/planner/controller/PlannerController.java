package org.example.planner.controller;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.PlannerRequestDto;
import org.example.planner.dto.PlannerResponseDto;
import org.example.planner.service.PlannerService;
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

    @PostMapping
    public ResponseEntity<PlannerResponseDto> savePlanner(@Validated @RequestBody PlannerRequestDto dto) {
        PlannerResponseDto responseDto = plannerService.savePlanner(dto.getTitle(), dto.getContents());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PlannerResponseDto>> findAll() {
        return new ResponseEntity<>(plannerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlannerResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(plannerService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlannerResponseDto> updateById(
            @PathVariable Long id,
            @Validated @RequestBody PlannerRequestDto dto
    ) {
        PlannerResponseDto responseDto = plannerService.updateById(id, dto.getTitle(), dto.getContents());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        plannerService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}