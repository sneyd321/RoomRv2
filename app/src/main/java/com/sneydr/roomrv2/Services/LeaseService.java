package com.sneydr.roomrv2.Services;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.rabbitmq.client.Channel;
import com.sneydr.roomrv2.Fragments.GenerateLeaseFragment;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.RabbitMQ.ConsumeFailedCallback;
import com.sneydr.roomrv2.RabbitMQ.ConsumeLeaseCallback;
import com.sneydr.roomrv2.RabbitMQ.RabbitCallback;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQ;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQObserver;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.sneydr.roomrv2.App.App.CHANNEL_ID;

public class LeaseService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getStringExtra("queueName") != null){
            String queueName = intent.getStringExtra("queueName");
            startForeground(1, buildIndeterminateNotification());
            RabbitTask task = new RabbitTask(queueName);
            task.execute();
        }
        return START_NOT_STICKY;
    }

    private Notification buildIndeterminateNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Downloading...")
                .setContentText("Ontario Lease Agreement")
                .setSmallIcon(R.drawable.ic_assignment_black_24dp)
                .setProgress(0, 0, true)
                .build();
    }



    private class RabbitTask extends AsyncTask<Void, Void, Void> {

        private String queueName;

        public RabbitTask(String queueName) {
            this.queueName = queueName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            RabbitMQ rabbitMQ = RabbitMQ.getInstance();
            try {
                rabbitMQ.initQueueAndExchange(queueName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
