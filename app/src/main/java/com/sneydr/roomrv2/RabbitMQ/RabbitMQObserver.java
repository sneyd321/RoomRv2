package com.sneydr.roomrv2.RabbitMQ;


import java.io.File;
import java.io.OutputStream;

public interface RabbitMQObserver {


    void onLease(byte[] bytes);


}
