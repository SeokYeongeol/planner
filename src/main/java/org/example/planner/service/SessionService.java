package org.example.planner.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
public class SessionService {
    private final UserRepository userRepository;

    // 세션 생성 메서드
    @Transactional
    public void createSession(
            HttpServletRequest request,
            HttpServletResponse response,
            UserResponseDto user
    ) {
        // 세션 생성 및 유지 시간 30분으로 지정
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        session.setMaxInactiveInterval(60 * 30);

        // 쿠키
        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
        sessionCookie.setPath("/");
        sessionCookie.setMaxAge(60 * 30);
        response.addCookie(sessionCookie);
    }
    
    // 세션 유지중인지 확인
    public Object getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session == null) return null;

        return session.getAttribute("user");
    }

    // 세션 로그인
    @Transactional
    public UserResponseDto loginSession(String email, String password) {
        User findUser = userRepository.findUserByEmailOrElseThrow(email);

        if(!findUser.equalsPassword(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다.");
        }

        return new UserResponseDto(findUser.getId(), findUser.getEmail(), findUser.getPassword());
    }

    // 세션 로그아웃
    @Transactional
    public void logoutSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) session.invalidate();
    }
}
