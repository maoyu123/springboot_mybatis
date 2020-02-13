package com.my.filter;

import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VerifyFilter extends OncePerRequestFilter {

    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(isProtectedUrl(request)){
            String verifyCode = request.getParameter("verifyCode");
            if(!validateVerify(verifyCode)){
                //手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",new DisabledException("验证码输入有误"));
                request.getRequestDispatcher("/login/error").forward(request,response);
            }else {
                filterChain.doFilter(request,response);
            }
        }else{
            filterChain.doFilter(request,response);
        }
    }

    private boolean validateVerify(String inputVerify){
        //获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String validateCode =((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();
        System.out.println("验证码："+validateCode + "用户输入的"+inputVerify);
        return validateCode.equals(inputVerify);
    }



    //拦截/Login的post
    private boolean isProtectedUrl(HttpServletRequest request){
        return "POST".equals(request.getMethod())&& pathMatcher.match("/login",request.getServletPath());
    }
}
