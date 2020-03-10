package com.my.activemq;

import com.my.SpringBootMybatisApplication;
import com.my.activemq.consume.QueueConsume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.jms.TextMessage;

/**
 * @author maoyu
 * @date 2020/3/10 11:24
 */
@SpringBootTest(classes = SpringBootMybatisApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestMQQueueConsume {

    @Autowired
    private QueueConsume queueConsume;
    private TextMessage textMessage;

    @Test
    public void testReceive()throws Exception{
        queueConsume.receive(textMessage);

    }
}
