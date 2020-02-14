package com.my.security;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取用户登录时携带的额外信息
 */
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private final String verifyCode;
    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.verifyCode = request.getParameter("verifyCode");
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    @Override
    public  String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(";VerifyCode:").append(this.getVerifyCode());
        return sb.toString();
    }

}
