package org.example.planner.domain.user.repository;

import org.example.planner.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 해당 email의 User을 가져옴
    Optional<User> findUserByEmail(String email);

    // User 이메일이 다를 때 예외처리
    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 없습니다."));
    }

    // User Id가 다를 때 예외처리
    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID를 가진 유저가 없습니다."));
    }
}
