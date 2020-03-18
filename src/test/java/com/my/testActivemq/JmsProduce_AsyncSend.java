package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * @author maoyu
 * @date 2020/3/18 9:47
 */
public class JmsProduce_AsyncSend {
    public static final String ACTIVEMQ_URL = "tcp://192.168.31.224:61617";
    public static final String QUEUE_NAME="queue-AsyncSend";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //设置useAsyncSened为true，让消息发送更快(此时的mq宕机，生产者中尚未通过send发送到mq的消息会丢失)
        activeMQConnectionFactory.setUseAsyncSend(true);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer)session.createProducer(queue);
        TextMessage textMessage = null;

        for(int i = 0; i <= 2 ;i++){
            textMessage = session.createTextMessage("message~~~~~AsyncSend" + i);
//            activeMQMessageProducer.send(textMessage);
            //设置消息头，该部分消息有唯一标识
            textMessage.setJMSMessageID(UUID.randomUUID().toString());
            String jmsMessageID = textMessage.getJMSMessageID();
            //使用jsmMessageID，添加回调函数确认消息发送成功和失败
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(jmsMessageID+",send success");
                }

                @Override
                public void onException(JMSException e) {
                    System.out.println(jmsMessageID+"send false");
                }
            });
        }
        activeMQMessageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送到队列完成");
    }
}
