package com.sneydr.roomrv2.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQ {

    private final String RABBIT_HOST = "192.168.0.115";

    private static RabbitMQ instance = null;

    public static RabbitMQ getInstance() {
        if (instance == null) {
            instance = new RabbitMQ();
        }
        return instance;
    }


    private Connection getRabbitMQConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBIT_HOST);
        try {
            return factory.newConnection();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Channel initQueueAndExchange(String queueName) throws IOException {
        Connection connection = getRabbitMQConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, "PDF-direct", "pdf");
        return channel;
    }

}
