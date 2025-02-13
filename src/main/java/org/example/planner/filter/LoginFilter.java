package org.example.planner.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter implements Filter {
    // 로그인 없이 접근 가능한 URI
    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        if (!isWhiteList(requestURI)) {
            HttpSession session = request.getSession(false);

            // 로그인 상태가 아니면 Postman에 401 StatusCode 반환
            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 화이트 리스트 검사
    public boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
