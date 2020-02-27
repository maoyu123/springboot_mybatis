package com.my.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author maoyu
 * @date 2020/2/27 15:26
 */
public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken)authentication;
        String mobile = (String)smsAuthenticationToken.getPrincipal();
        checkSmsCode(mobile);
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        //鉴权成功，重新生成一个new SmsAuthenticationToken
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(userDetails,userDetails.getAuthorities());
        authenticationResult.setDetails(smsAuthenticationToken.getDetails());
        return authenticationResult;
    }

    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter("smsCode");

        Map<String, Object> smsCode = (Map<String, Object>) request.getSession().getAttribute("smsCode");
        if(smsCode ==null){
            throw new BadCredentialsException("未检测到手机验证码");
        }
        String applyMobile = smsCode.get("mobile").toString();
        int code = (int) smsCode.get("code");

        if(!applyMobile.equals(mobile)){
            throw new BadCredentialsException("申请手机号与登录的手机号不一致");
        }
        if(code != Integer.parseInt(inputCode)){
            throw new BadCredentialsException("输入的验证码错误");
        }
    }

    //此处参考AbstractUserDetailsAuthenticationProvider中supports方法返回
    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
