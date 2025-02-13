package org.example.planner.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.planner.config.PasswordEncoder;
import org.example.planner.domain.user.dto.*;
import org.example.planner.domain.user.service.LoginService;
import org.example.planner.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final LoginService loginService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> saveUser(
            @Validated @RequestBody UserRequestDto dto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UserResponseDto responseDto = userService.saveUser(dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail());

        loginService.createSession(request, response, responseDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(
            @Validated @RequestBody LoginUserRequestDto dto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UserResponseDto responseDto = loginService.loginSession(dto.getEmail(), dto.getPassword());

        loginService.createSession(request, response, responseDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpServletRequest request) {
        loginService.logoutSession(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 현재 세션이 있는지 확인
    @GetMapping("/sessions")
    public ResponseEntity<UserResponseDto> currentUser(HttpServletRequest request) {
        UserResponseDto user = (UserResponseDto) loginService.getSessionUser(request);

        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 상태가 아닙니다.");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 해당 아이디의 유저 확인
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @Validated @RequestBody UpdatePasswordRequestDto dto
    ) {
        userService.updatePassword(id, dto.getOldPassword(), dto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
