package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Network.Observables.LeaseObservable;
import com.sneydr.roomrv2.Network.Observers.LeaseObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetLeaseCallback extends NetworkCallback implements LeaseObservable {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()) {
            notifyLease();
            return;
        }
        notifyFailure(response.body().string());
        response.close();
    }

    @Override
    public void notifyLease() {
        LeaseObserver leaseObserver = (LeaseObserver) this.observer;
        leaseObserver.onLease();

    }
}
