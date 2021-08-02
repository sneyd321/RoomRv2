package com.sneydr.roomrv2.Services.State;

import com.sneydr.roomrv2.App.NotificationHelper;
import com.sneydr.roomrv2.App.Permission;

public class NotificationContext {

    private NotificationState notificationState;

    public NotificationContext() {
        this.notificationState = new PendingState();
    }

    public void setState(NotificationState notificationState){
        this.notificationState = notificationState;
    }

    public NotificationState getState() {
        return this.notificationState;
    }

}
