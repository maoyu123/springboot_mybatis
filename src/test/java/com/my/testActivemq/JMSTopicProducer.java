package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author maoyu
 * @date 2020/3/6 16:57
 */
public class JMSTopicProducer {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.224:61617");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Topic-Persist");
        TextMessage textMessage = session.createTextMessage("maoge持久化消息：topic");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
        for(int i = 1;i<=3;i++){
            producer.send(textMessage);
        }
        producer.close();
        session.close();
        connection.close();
    }
}
