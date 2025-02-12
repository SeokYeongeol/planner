package org.example.planner.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.planner.config.PasswordEncoder;
import org.example.planner.dto.UserResponseDto;
import org.example.planner.entity.User;
import org.example.planner.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class SessionUtils {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 세션 생성 메서드
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

    // 현재 상태의 HttpServletRequest를 가져오는 메서드
    private static HttpServletRequest currentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    // 로그인 상태인지 확인해 상황을 결정할 메서드
    public boolean isLoggedIn() {
        Object user = getSessionUser(currentRequest());

        return user != null;
    }

    // 세션 로그인
    public UserResponseDto loginSession(String email, String password) {
        User findUser = userRepository.findUserByEmailOrElseThrow(email);

        // 해당 유저의 비밀번호와 입력한 현재 비밀번호가 같지 않을 때 예외처리
        if(!passwordEncoder.matches(passwordEncoder.encode(password), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "입력하신 비밀번호가 일치하지 않습니다.");
        }

        return new UserResponseDto(findUser.getId(), findUser.getEmail(), findUser.getPassword());
    }

    // 세션 로그아웃
    public void logoutSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if(session != null) session.invalidate();
    }
}