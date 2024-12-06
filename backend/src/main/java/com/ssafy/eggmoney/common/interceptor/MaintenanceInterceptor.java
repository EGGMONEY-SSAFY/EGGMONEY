package com.ssafy.eggmoney.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalTime;

@Component
public class MaintenanceInterceptor implements HandlerInterceptor {
    private static final LocalTime MAINTENANCE_START = LocalTime.of(16, 50);
    private static final LocalTime MAINTENANCE_END = LocalTime.of(16, 55);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isAfter(MAINTENANCE_START) && currentTime.isBefore(MAINTENANCE_END)) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE); // 503 상태 코드
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain; charset=UTF-8");
            response.getWriter().write("서버 점검 중입니다. 잠시 후 다시 시도해주세요.");
            return false;
        }

        return true;
    }
}
