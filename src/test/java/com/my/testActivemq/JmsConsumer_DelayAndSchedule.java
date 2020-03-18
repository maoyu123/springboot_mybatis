package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author maoyu
 * @date 2020/3/18 11:08
 */
public class JmsConsumer_DelayAndSchedule {
    public static final String ACTIVEMQ_URL ="tcp://192.168.31.224:61617";
    public static final String QUEUE_NAME ="queue-Delay";
    public static void main(String[] args) throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.创建连接对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建session会话
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用会话对象创建目标对象，包含queue和topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //6.使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7.向consumer对象中设置messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(null!= message && message instanceof TextMessage){
                    TextMessage textMessage= (TextMessage)message;
                    if(textMessage != null){
                        try {
                            System.out.println("消费者接收到消息"+textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        //8.程序等待接收用户消息
        System.in.read();
        //9.关闭资源
        consumer.close();
        session.commit();
        session.close();
        connection.close();
    }
}
