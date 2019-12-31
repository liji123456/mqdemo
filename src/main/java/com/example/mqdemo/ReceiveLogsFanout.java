package com.example.mqdemo;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/12/25
 * @Content:
 */
@Slf4j
public class ReceiveLogsFanout {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            //声明交换机，随机队列，绑定交换机，进行消费消息
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String queueName =channel.queueDeclare().getQueue();
            log.info("queueName:{}",queueName);
            channel.queueBind(queueName,EXCHANGE_NAME,"");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback =(consumerTag,delivery)->{
              String message =new String(delivery.getBody(),"UTF-8");
              log.info("Received :{}",message);
            };
            channel.basicConsume(queueName,true,deliverCallback,consumerTag ->{});
        }
    }
}
