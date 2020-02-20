package com.my.security;

import com.my.filter.VerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired(required = false)
    private DataSource dataSource;
    @Autowired(required = false)
    private VerifyFilter verifyFilter;
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    @Autowired
    private CustomAuthencationProvider customAuthencationProvider;
    @Autowired
    private CustomAuthentationSuccessHandler customAuthentationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private AuthenticationManagerBuilder auth;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，请注释掉这条语句，否则会报错。
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     * 注入自定义的PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }

        })*/
        auth.authenticationProvider(customAuthencationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //匿名的url，填写在下边 这样在未登录状态下也能正常访问
                .antMatchers("/getVerifyCode","/login/invalid").permitAll()
                .anyRequest().authenticated()
                .and()
                //设置登录页
                .formLogin().loginPage("/login")
                /*//设置登录成功页
                .defaultSuccessUrl("/").permitAll()
                //添加登录失败异常处理
                .failureUrl("/login/error")*/
                .successHandler(customAuthentationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
                /*//自定义用户名和密码 ,默认为taylor swift
                .usernameParameter("taylor")
                .passwordParameter("swift")*/
                //指定authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                /*//增加addFilterBefore()作用:在参数二之前执行参数一设置的过滤器
                //spring security对于用户名面登录方式是通过UsernamePasswordAuthenticationFilter处理的，在这之前执行验证过滤器
                .addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)*/
                .and().logout().permitAll()
                .and()
                .sessionManagement()
                    .invalidSessionUrl("/login/invalid")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .expiredSessionStrategy(new CustomExpiredSessionStrategy());
                //添加自动登录
//                .and()
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
                //有效时间：单位s
//                .tokenValiditySeconds(60).userDetailsService(userDetailsService);

                //关闭scrf 跨域
                http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**","/js/**");
    }
}

