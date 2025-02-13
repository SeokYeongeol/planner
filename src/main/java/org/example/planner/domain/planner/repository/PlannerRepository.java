package org.example.planner.domain.planner.repository;

import org.example.planner.domain.planner.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    default Planner findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디의 일정이 없습니다."));
    }

    // Planner의 해당 유저 리스트를 가져옴
    List<Planner> findAllByUser_Id(Long id);
}
