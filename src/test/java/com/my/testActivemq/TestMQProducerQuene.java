package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @author maoyu
 * @date 2020/3/4 11:29
 */
public class TestMQProducerQuene {

    public void testMQProducerQueue() throws JMSException {
        //1.创建工厂连接对象，指定ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.224:61617");
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2.使用连接工厂创建连接对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.使用连接对象创建会话(session)对象
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
        //5.使用会话对象创建目标对象
        Queue queue = session.createQueue("test-queue-jdbc");
        //6.使用会话对象创建生产者对象
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //7.使用会话对象创建一个消息对象
        TextMessage textMessage = session.createTextMessage("hello!JDBC!galgadot");
        //8.发送消息
        producer.send(textMessage);
        //9.关闭资源
        producer.close();
        session.commit();
        session.close();
        connection.close();
    }
    //接收代码
    public void TestMQConsumerQueue() throws JMSException, IOException {
        //1.创建连接工厂，指定ip和port
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.224:61617");
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2.创建连接对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接
        connection.start();
        //4.创建session会话
        Session session = connection.createSession(true,Session.CLIENT_ACKNOWLEDGE);
        //5.使用会话对象创建目标对象，包含queue和topic
        Queue queue = session.createQueue("test-queue-jdbc");
        //6.使用会话对象创建生产者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7.向consumer对象中设置messageListener对象，用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage){
                    TextMessage textMessage= (TextMessage)message;
                    if(textMessage != null){
                        try {
                            System.out.println("消费者接受到消息"+textMessage.getText());
//                            textMessage.acknowledge();
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
