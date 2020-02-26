package com.my.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping("/")
    public String showHome(){
        //判断是否有对应权限
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登录用户："+name);
        return "home.html";
    }

    @RequestMapping("/login")
    public String showLogin(){
        return  "login.html";
    }

    @RequestMapping("/login/error")
    public void loginError(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        AuthenticationException exception =
                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        try {
            response.getWriter().write(exception.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


   /* @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin(){
        return "你有ROLE_ADMIN角色";
    }*/

    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','r')")
    public String printAdminR(){
        return "有访问admin路径有/r的权限";
    }

    @RequestMapping("/admin/c")
    @ResponseBody
    @PreAuthorize("hasPermission('/admin','c')")
    public String printAdminC(){
        return "有访问admin路径有/c的权限";
    }

    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "如果你看见这句话，说明你有ROLE_USER角色";
    }

    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public String invalid(){
        return  "Session 已过期，请重新登录";
    }

    @GetMapping("/kick")
    @ResponseBody
    public  String removeUserSessionByUsername(@RequestParam String username){
        int count = 0;
        //获取session中所有用户信息
        List<Object> users = sessionRegistry.getAllPrincipals();
        for(Object principal:users){
            //判断指定用户的principal  信息和输入的用户信息是否一致
            if(principal instanceof User){
                String principalName = ((User) principal).getUsername();
                if(principalName.equals(username)){
                    //参数二: 是否包含过期的session
                    List<SessionInformation> sessionInfo = sessionRegistry.getAllSessions(principal, false);
                    if(null != sessionInfo && sessionInfo.size()>0){
                            for (SessionInformation sessionInformation:sessionInfo){
                                sessionInformation.expireNow();
                                count++;
                            }
                    }
                }
            }
        }
        return  "操作成功，共计剔除session"+count +"个";
    }

    //获取用户认证信息
    @RequestMapping("/me")
    @ResponseBody
    public Object me(Authentication authentication){
        return  authentication;
    }
    @RequestMapping("/she")
    @ResponseBody
    public Object me(@AuthenticationPrincipal UserDetails userDetails){
        return userDetails;
    }
}