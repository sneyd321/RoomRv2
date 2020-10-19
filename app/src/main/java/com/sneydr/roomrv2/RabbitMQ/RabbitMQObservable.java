package com.sneydr.roomrv2.RabbitMQ;

import com.sneydr.roomrv2.Entities.House.Lease;

import java.io.File;
import java.io.OutputStream;

public interface RabbitMQObservable {


    void registerObserver(RabbitMQObserver observer);

    void clearObserver();

    void notifyLease(byte[] bytes);


}
