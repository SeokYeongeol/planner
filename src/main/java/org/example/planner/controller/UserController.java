package org.example.planner.controller;

import lombok.RequiredArgsConstructor;
import org.example.planner.dto.UserRequestDto;
import org.example.planner.dto.UserResponseDto;
import org.example.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<UserResponseDto> saveUser(@Validated @RequestBody UserRequestDto dto) {
        UserResponseDto responseDto = userService.saveUser(dto.getUsername(), dto.getPassword(), dto.getEmail());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
