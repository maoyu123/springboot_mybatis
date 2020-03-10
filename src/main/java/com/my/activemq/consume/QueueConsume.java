package com.my.activemq.consume;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author maoyu
 * @date 2020/3/10 11:14
 */
@Component
public class QueueConsume {
    @JmsListener(destination = "${myqueue}")
    public void receive(TextMessage textMessage) throws JMSException{
        System.out.println("消费者接受消息"+textMessage.getText());
    }
}
