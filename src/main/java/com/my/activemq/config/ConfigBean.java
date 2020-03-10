package com.my.activemq.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author maoyu
 * @date 2020/3/9 13:27
 */
@Component
@EnableJms
public class ConfigBean {

    @Value("${myqueue}")
    private String myQueue;
    @Value("${mytopic}")
    private String mytopic;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(mytopic);
    }
}
