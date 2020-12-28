package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

public interface InternetAvailableObservable extends NetworkObservable {

    void notifyInternetNotAvailable(String text);
}
