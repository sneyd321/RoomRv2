package com.sneydr.roomrv2.RabbitMQ;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Network.NetworkObservable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
