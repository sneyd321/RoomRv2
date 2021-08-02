package com.sneydr.roomrv2.Services.State;

import androidx.core.app.NotificationCompat;

import com.sneydr.roomrv2.App.NotificationHelper;
import com.sneydr.roomrv2.R;

import java.util.Map;

public class FailureState extends NotificationState {
    @Override
    public void buildNotification(NotificationHelper notificationHelper, Map<String, Object> map) {
        notificationHelper.setContentTitle(getContentTitle(map));
        notificationHelper.setContentText("Failed to upload");
        notificationHelper.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notificationHelper.setPriority(NotificationCompat.PRIORITY_LOW);
        notificationHelper.setProgress(100);
        notificationHelper.build();
    }
}
