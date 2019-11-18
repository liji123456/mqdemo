package com.example.mqdemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/11/18
 * @Content:
 */
public class ReceiveLogsDirect {
    private static final  String EXCHAGNE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHAGNE_NAME, BuiltinExchangeType.DIRECT);
        String queueName =channel.queueDeclare().getQueue();
        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }

    }
}
