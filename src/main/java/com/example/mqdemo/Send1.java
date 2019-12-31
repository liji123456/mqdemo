package com.example.mqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/10/16
 * @Content:
 */

public class Send1 {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //声明队列是幂等的-仅当队列不存在时才创建
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "hello world";
//            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes("utf-8"));
            //持久化消息 不一定能保存到硬盘，但已足够，可以加发布者确认加强保证
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("utf-8"));

            System.out.println("send " + msg);
        }

    }
}
