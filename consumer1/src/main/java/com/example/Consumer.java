package com.example;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2024/2/22 16:55
 * @Created by weixiao
 */
public class Consumer {
    private static final RabbitmqConfig CONFIG = new RabbitmqConfig();
    private static final String QUEUE_NAME = CONFIG.getRmqMainQueueName();
    private static final ConcurrentHashMap<String, MessageData> liftRidesMap = new ConcurrentHashMap<>();
    private static final Gson gson = new Gson();
    private static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(CONFIG.getRmqHostAddress());
        factory.setUsername(CONFIG.getRmqUsername());
        factory.setPassword(CONFIG.getRmqPassword());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();
                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                    DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                        processMessage(message);
                    };
                    channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
                    });
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void processMessage(String message) {
        MessageData messageData = gson.fromJson(message, MessageData.class);
        liftRidesMap.putIfAbsent(String.valueOf(counter.addAndGet(1)), messageData);
        System.out.println("map size now: " + liftRidesMap.size());
    }
}
