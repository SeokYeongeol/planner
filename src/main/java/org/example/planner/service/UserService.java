package org.example.planner.service;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.UserResponseDto;
import org.example.planner.entity.User;
import org.example.planner.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto saveUser(String username, String password, String email) {
        User savedUser = userRepository.save(new User(username, password, email));

        return new UserResponseDto(savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }

    public void delete(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }
}
