package org.example.planner.repository;

import org.example.planner.entity.Planner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    default Planner findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디의 일정이 없습니다."));
    }

    // Planner의 해당 유저 리스트를 가져옴
    List<Planner> findAllByUserUsername(String username);

    // Id와 username이 같은 Planner를 가져옴
    Optional<Planner> findByIdAndUserUsername(Long id, String username);

    // findByIdAndUserUsername 의 예외처리
    default Planner findByIdAndUserUsernameOrElseThrow(Long id, String username) {
        return findByIdAndUserUsername(id, username)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 조건에 일치하지 않습니다."));
    }
}
