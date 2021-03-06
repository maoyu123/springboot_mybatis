package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author maoyu
 * @date 2020/3/4 17:53
 */
public class TestTopic {
    public void testMQTopicProducerQueue() throws JMSException {
        //1.创建工厂连接对象，指定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.224:61617");
        //2.使用连接工厂创建连接对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.使用连接对象创建会话(session)对象
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用会话对象创建目标对象
        Queue queue = session.createQueue("test-topic");
        //6.使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        //7.使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!test-topic!galgadot");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.close();
        connection.close();
    }
    //接收代码
    public void TestMQTopicConsumerQueue() throws JMSException, IOException {
        //1.创建连接工厂，指定ip和port
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.224:61617");
        //2.创建连接对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建session会话
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //5.使用会话对象创建目标对象，包含queue和topic
        Queue queue = session.createQueue("test-topic");
        //6.使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7.向consumer对象中设置messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage){
                    TextMessage textMessage= (TextMessage)message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //8.程序等待接收用户消息
        System.in.read();
        //9.关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
