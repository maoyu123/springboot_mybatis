package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author maoyu
 * @date 2020/3/6 17:12
 */
public class JMSTopicConsumer {
    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("************maoge");
        ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.31.224:61617");
        Connection connection = factory.createConnection();
        connection.setClientID("123456##########");
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Topic-Persist");
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic,"remark....");
        connection.start();
        Message message = topicSubscriber.receive();
            while (null != message){
                TextMessage textMessage =(TextMessage)message;
                System.out.println("持久化message"+textMessage.getText());
                message = topicSubscriber.receive();
            }
        session.close();
        connection.close();

    }
}
