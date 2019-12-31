package com.example.mqdemo;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author:liji
 * @Date:2019/12/25
 * @Content:
 * 逻辑类似于直接交换的逻辑
 *（星号）可以代替一个单词。
＃（哈希）可以替代零个或多个单词。

 */

@Slf4j
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //声明交换机类型为TOPIC
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String routingKey  = getSeverity(args);
            String message = getMessage(args);
            //绑定routingKey:info，发送消息到交换机
            channel.basicPublish(EXCHANGE_NAME, routingKey , null, message.getBytes("utf-8"));
            log.info(" [x] Sent '" + routingKey  + "':'" + message + "'");

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
