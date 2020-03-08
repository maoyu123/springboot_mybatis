package com.my.testActivemq;

import org.apache.activemq.broker.BrokerService;

/**
 * @author maoyu
 * @date 2020/3/8 19:22
 */
public class EmbedBroker {
    public static void main(String[] args) throws Exception {
        BrokerService brokerService =  new BrokerService();
        //将activeMQ嵌入到java程序中
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
