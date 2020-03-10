package com.my.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;
import java.util.UUID;

/**
 * @author maoyu
 * @date 2020/3/10 14:40
 */
@Component
public class TopicProduce {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Topic topic;

    @Scheduled(fixedDelay = 3000)
    public void pubMsg(){
        jmsMessagingTemplate.convertAndSend(topic,"#############"+ UUID.randomUUID().toString().substring(0,6));
        System.out.println("定时发送topic主题消息完成！！！！！");
    }
}
