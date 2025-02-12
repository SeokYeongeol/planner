package org.example.planner.service;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.UserResponseDto;
import org.example.planner.entity.User;
import org.example.planner.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    // 유저 생성 메서드
    @Transactional
    public UserResponseDto saveUser(String username, String password, String email) {
        User savedUser = userRepository.save(new User(username, password, email));

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    // 해당 유저 조회 메서드
    public UserResponseDto findById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        return new UserResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());
    }

    // 비밀번호 변경 메서드
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        // 해당 유저의 비밀번호와 입력한 현재 비밀번호가 같지 않을 때 예외처리
        if(!findUser.equalsPassword(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "입력하신 현재 비밀번호가 같지 않습니다.");
        }

        findUser.setPassword(newPassword);
    }

    // 해당 유저 삭제 메서드
    @Transactional
    public void delete(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }
}
