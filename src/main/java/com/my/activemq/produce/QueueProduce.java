package com.my.activemq.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @author maoyu
 * @date 2020/3/9 13:26
 */
@Component
public class QueueProduce {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    /**
     *javax.jms.Queue; 切记切记
     */
    @Autowired
    private Queue queue;

    @Bean
    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"********"+ UUID.randomUUID().toString().substring(0,6));
        System.out.println("produceMsg 完成！！！！！！");
    }

    /**
     * 开启定时投放消息 Scheduled ，在启动类中 添加@EnableScheduling
     */
    @Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(queue,"********Scheduled:"+ UUID.randomUUID().toString().substring(0,6));
        System.out.println("produceMsg  Scheduled send 完成！！！！！！");
    }
}
