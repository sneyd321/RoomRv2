package com.sneydr.roomrv2.RabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sneydr.roomrv2.Services.LeaseService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQ {

    private final String RABBIT_HOST = "104.197.86.237";

    private static RabbitMQ instance = null;
    private RabbitMQObserver observer;

    public static RabbitMQ getInstance() {
        if (instance == null) {
            instance = new RabbitMQ();
        }
        return instance;
    }


    private Connection getRabbitMQConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RABBIT_HOST);
        factory.setPort(5672);
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
        RabbitCallback successCallback = new ConsumeLeaseCallback();
        if (observer != null) {
            successCallback.registerObserver(observer);
        }
        channel.basicConsume(queueName, true, successCallback, new ConsumeFailedCallback());
        return channel;
    }


    public void registerObserver(RabbitMQObserver observer) {
        this.observer = observer;
    }







}
