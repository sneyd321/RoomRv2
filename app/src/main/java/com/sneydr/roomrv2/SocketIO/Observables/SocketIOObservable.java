package com.sneydr.roomrv2.SocketIO.Observables;

import com.sneydr.roomrv2.SocketIO.Observers.SocketIOObserver;

public interface SocketIOObservable {

    void registerObserver(SocketIOObserver observer);

    void clearObserver();


}
