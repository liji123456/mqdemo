package com.example.mqdemo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/10/16
 * @Content:
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
       try(Connection connection = factory.newConnection();
           Channel channel = connection.createChannel()){
//           channel.queueDeclare(TASK_QUEUE_NAME)
       }
//        channel.queueDeclare(TASK_QUEUE_NAME,)
    }
}
