package com.sneydr.roomrv2.Services.State;

import androidx.core.app.NotificationCompat;

import com.sneydr.roomrv2.App.NotificationHelper;
import com.sneydr.roomrv2.R;

import java.util.Map;

public class PendingState extends NotificationState {

    @Override
    public void buildNotification(NotificationHelper notificationHelper, Map<String, Object> map) {
        notificationHelper.setContentTitle(getContentTitle(map));
        notificationHelper.setContentText(getContentTitle(map));
        notificationHelper.setSmallIcon(R.drawable.ic_notifications_black_24dp);
        notificationHelper.setIndeterminateProgress();
        notificationHelper.setPriority(NotificationCompat.PRIORITY_LOW);
        notificationHelper.build();
    }
}
