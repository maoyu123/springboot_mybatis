package com.my.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();
        CustomWebAuthenticationDetails customWebAuthenticationDetails =(CustomWebAuthenticationDetails)authentication.getDetails();
        String verifyCode = customWebAuthenticationDetails.getVerifyCode();
        if(!validateVerify(verifyCode)){
            throw new DisabledException("验证码输入有误");
        }

        //userDetails 是从数据库中查询到的用户信息
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(inputName);
        if(!userDetails.getPassword().equals(inputPassword)){
            throw new BadCredentialsException("密码输入错误");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,inputPassword,userDetails.getAuthorities());
    }

    private boolean validateVerify(String inputVerify){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String validateCode = ((String)request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();
        System.out.println("验证码："+validateCode + ",用户输入："+inputVerify);
        return  validateCode.equals(inputVerify);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
