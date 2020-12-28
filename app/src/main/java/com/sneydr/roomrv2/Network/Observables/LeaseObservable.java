package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.House.Lease;

public interface LeaseObservable extends NetworkObservable {

    void notifyLease();

}
