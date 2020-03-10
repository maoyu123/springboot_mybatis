package com.my.activemq.consume;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author maoyu
 * @date 2020/3/10 14:40
 */
@Component
public class TopicConsume {
    @JmsListener(destination = "${mytopic}")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("消费者接收到订阅的主题"+textMessage.getText());
    }
}
