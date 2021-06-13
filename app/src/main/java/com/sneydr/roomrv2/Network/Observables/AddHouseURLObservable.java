package com.sneydr.roomrv2.Network.Observables;

public interface AddHouseURLObservable extends NetworkObservable {

    void notifyObserver(String url);

    void notifyFormCompete(String message);
}
