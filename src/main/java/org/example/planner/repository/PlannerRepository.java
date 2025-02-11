package org.example.planner.repository;

import org.example.planner.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    default Planner findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디의 일정이 없습니다."));
    }
}
