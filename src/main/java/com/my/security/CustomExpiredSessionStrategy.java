package com.my.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author maoyu
 * @date 2020/2/20 21:05
 */
@Component
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy{

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","有另外一台机器登录，你被迫下线"+event.getSessionInformation().getLastRequest());

        String s = objectMapper.writeValueAsString(map);
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(s);
    }
}
