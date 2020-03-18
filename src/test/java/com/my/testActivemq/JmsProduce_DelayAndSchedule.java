package com.my.testActivemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * @author maoyu
 * @date 2020/3/18 11:08
 */
public class JmsProduce_DelayAndSchedule {
    public static final String ACTIVEMQ_URL = "tcp://192.168.31.224:61617";
    public static final String QUEUE_NAME="queue-Delay";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        TextMessage textMessage = null;
        Long delay = 1000*3L;
        Long period = 1000*4L;
        int repeat = 5 ;

        for(int i = 0; i <= 2 ;i++){
            textMessage = session.createTextMessage("message~~~~~Delay" + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送到队列完成");
    }
}
