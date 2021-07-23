package com.sneydr.roomrv2.Services;

import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

public interface NotificationObservable {

    void registerObserver(NotificationObserver notificationObserver);

    void clearObserver();

    void notifyFailure(String tag, String response);

    void notifySuccess(String data);

}
