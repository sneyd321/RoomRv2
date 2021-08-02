package com.sneydr.roomrv2.Services.State;

import com.sneydr.roomrv2.App.NotificationHelper;

import java.util.Map;

public abstract class NotificationState {

    public abstract void buildNotification(NotificationHelper notificationHelper, Map<String, Object> map);

    protected String getContentText(Map<String, Object> document) {
        String contentText = (String) document.get("notificationText");
        if (contentText == null || contentText.isEmpty())
            return "Uploading...";
        return contentText;
    }

    protected String getContentTitle(Map<String, Object> document) {
        String contentTitle = (String) document.get("notificationName");
        if (contentTitle == null || contentTitle.isEmpty())
            return "RoomR";
        return contentTitle;
    }

}
