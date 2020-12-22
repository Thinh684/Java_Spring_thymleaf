package agie.poly.homestay;


import agie.poly.homestay.entity.TaiKhoan;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationConfig implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().equals("/admin/login")) {
            TaiKhoan user = (TaiKhoan) request.getSession().getAttribute("userPageAdmin");
            if (user == null) {
                response.sendRedirect("/admin/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
