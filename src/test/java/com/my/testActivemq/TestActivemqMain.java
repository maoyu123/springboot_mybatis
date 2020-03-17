package com.my.testActivemq;

import javax.jms.JMSException;
import java.io.IOException;

/**
 * @author maoyu
 * @date 2020/3/4 17:33
 */
public class TestActivemqMain {
    public static void main(String[] args) throws JMSException, IOException {
        TestMQProducerQuene testMQProducerQuene = new TestMQProducerQuene();
//        testMQProducerQuene.testMQProducerQueue();
        testMQProducerQuene.TestMQConsumerQueue();
        /*TestTopic testTopic = new TestTopic();
        testTopic.TestMQTopicConsumerQueue();
        testTopic.testMQTopicProducerQueue();*/
        //测试事务
      /*  TestMQProducerQuene testMQProducerQuene = new TestMQProducerQuene();
        testMQProducerQuene.testMQProducerQueue();
        testMQProducerQuene.TestMQConsumerQueue();*/

    }
}
