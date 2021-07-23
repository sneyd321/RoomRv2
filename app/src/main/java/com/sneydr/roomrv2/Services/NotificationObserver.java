package com.sneydr.roomrv2.Services;

public interface NotificationObserver {

    void onFailure(String tag, String response);

    void onNotification(String data);
}
