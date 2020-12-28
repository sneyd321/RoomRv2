package com.sneydr.roomrv2.Repositories;

import android.app.Application;

import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class LeaseRepository extends Repository {


    public LeaseRepository(Application application) {
        super(application);
    }

    public void sendLeaseData(Lease lease, String authToken, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.postLease(lease, authToken);
            network.send(request, NetworkCallbackType.GetLease, observer);
        }

    }

}
