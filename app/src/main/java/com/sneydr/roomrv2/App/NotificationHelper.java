package com.sneydr.roomrv2.App;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.sneydr.roomrv2.App.App.CHANNEL_ID;

public class NotificationHelper {

    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notificationManager;
    private int PROGRESS_CURRENT;

    public NotificationHelper(Context context) {
         this.builder = new NotificationCompat.Builder(context, CHANNEL_ID);
         this.notificationManager = NotificationManagerCompat.from(context);

    }

    public void setContentTitle(String title) {
        builder.setContentTitle(title);
    }

    public void setContentText(String text) {
        builder.setContentText(text);
    }

    public void setSmallIcon(int drawable) {
        builder.setSmallIcon(drawable);
    }

    public void setPriority(int priority) {
        builder.setPriority(NotificationCompat.PRIORITY_LOW);
    }

    public void setProgress(int progress) {
        int PROGRESS_MAX = 100;
        builder.setProgress(PROGRESS_MAX, progress, false);
    }

    public void setIndeterminateProgress() {
        builder.setProgress(0, 0, true);
    }

    public Notification getNotification() {
        return builder.build();
    }



    public void build() {
        notificationManager.notify(1, builder.build());
    }


}
