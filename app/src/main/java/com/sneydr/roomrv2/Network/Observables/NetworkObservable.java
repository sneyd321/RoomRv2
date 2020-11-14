package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.util.List;

public interface NetworkObservable {

    void registerObserver(NetworkObserver networkObserver);

    void clearObserver();

    void notifyFailure(String response);
}
