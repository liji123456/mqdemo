package com.example.mqdemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/11/18
 * @Content: 只能进行无意识的广播
 */
@Slf4j
public class EmitLogFanout {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            //声明交换机类型为 FANOUT
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
            String message = getMessage(args);
            //发送消息
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("utf-8"));
            log.info(" [x] Sent '" +  message + "'");

        }
    }

    private static String getSeverity(String[] strings) {
        if (strings.length < 1){   return "info";}
        return strings[0];
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 2){
            return "Hello World!";}
        return joinStrings(strings, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0){ return "";}
        if (length <= startIndex) {return "";}
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
