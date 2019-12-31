package com.example.mqdemo;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;


/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/11/18
 * @Content:
 */
@Slf4j
public class ReceiveLogsDirect {
    private static final  String EXCHAGNE_NAME="direct_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHAGNE_NAME, BuiltinExchangeType.DIRECT);
        //创建随机队列（服务停止，自动删除）
        String queueName =channel.queueDeclare().getQueue();
        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
            System.exit(1);
        }
        //交换机 绑定队列和路由key:info
        for (String severity:args){
            channel.queueBind(queueName,EXCHAGNE_NAME,severity);
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //回调
        DeliverCallback deliverCallback =(consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"UTF-8");
            log.info(delivery.getEnvelope().getRoutingKey()+":"+message);
        };
        //消费
        channel.basicConsume(queueName,true,deliverCallback,consumerTag->{});


    }
}
