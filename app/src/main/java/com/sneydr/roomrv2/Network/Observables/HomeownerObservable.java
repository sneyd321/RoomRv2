package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

public interface HomeownerObservable extends NetworkObservable {

    void notifyHomeowner(Homeowner homeowner);
}
