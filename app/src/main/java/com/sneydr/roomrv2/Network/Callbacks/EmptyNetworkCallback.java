package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.Observables.EmptyObservable;
import com.sneydr.roomrv2.Network.Observers.EmptyObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class EmptyNetworkCallback extends NetworkCallback     {

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        response.close();
    }


}
