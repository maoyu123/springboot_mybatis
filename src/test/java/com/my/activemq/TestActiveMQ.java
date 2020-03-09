package com.my.activemq;

import com.my.SpringBootMybatisApplication;
import com.my.activemq.produce.QueueProduce;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * @author maoyu
 * @date 2020/3/9 14:09
 */
@SpringBootTest(classes = SpringBootMybatisApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestActiveMQ {
    @Resource
    private QueueProduce queueProduce;
    @Test
    public void testSend()throws Exception{
        queueProduce.produceMsg();
    }

}
