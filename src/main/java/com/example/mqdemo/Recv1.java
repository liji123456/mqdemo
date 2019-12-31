package com.example.mqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/10/16
 * @Content:
 */
@Slf4j
public class Recv1 {
    /**
     * 被遗忘的 basicAck  会重复加入队列 耗内存 见查询命令
     */
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //TODO 会遇到的问题  没有足够可用磁盘空间的情况下启动的（默认情况下，它至少需要200 MB的可用空间）disk_free_limit
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //我们可能在发布者之前启用使用者所以要确保队列存在
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + msg + "'");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag ->{});


//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost("localhost");
//        Connection connection = factory.newConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//        //队列持久 (已有队列 不可以重新定义参数)
//        boolean durable = true;
////        channel.queueDeclare("hello", durable, false, false, null);
//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String msg = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received '" + msg + "'");
//        };
//        channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag ->{});

    }
}
