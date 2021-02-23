package com.sneydr.roomrv2.App;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import java.io.File;

public class App extends Application {

    public static final String CHANNEL_ID = "RoomRNotificationChannel";

    private static File file;
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        App.file = getCacheDir();
        App.application = this;

    }

    public static File getCache() {
        return App.file;
    }

    public static Application getApplication() {
        return App.application;
    }



    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "RoomR",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
