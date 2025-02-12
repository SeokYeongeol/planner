package org.example.planner.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter implements Filter {
    // 로그인 요청을 하지 않아도 되는 URI
    private static final String[] WHITE_LIST = {"/", "/users/signup","/users/login" ,"/users/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException
    {
        // 다양한 기능 사용을 위한 다운 캐스팅
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        // 로그인 체크 URI 검사
        if(!isWhiteList(requestURI)) {
            HttpSession session = request.getSession(false);

            if(session == null || session.getAttribute("user") == null) {
                throw new RuntimeException("로그인을 해주세요.");
            }
        }

        filterChain.doFilter(request, response);
    }

    // 로그인 여부를 확인하는 URI인지 확인하는 메서드
    public boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
