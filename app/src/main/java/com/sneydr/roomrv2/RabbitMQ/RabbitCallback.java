package com.sneydr.roomrv2.RabbitMQ;

import com.rabbitmq.client.DeliverCallback;

import java.util.ArrayList;
import java.util.List;

public abstract class RabbitCallback implements DeliverCallback, RabbitMQObservable {


    private List<RabbitMQObserver> observers;

    public RabbitCallback() {
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(RabbitMQObserver observer) {
        observers.add(observer);
    }

    @Override
    public void clearObserver() {
        observers.clear();
    }

    @Override
    public void notifyLease(byte[] bytes) {
        for (RabbitMQObserver observer : this.observers) {
            observer.onLease(bytes);
        }
    }



}
