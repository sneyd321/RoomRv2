package com.sneydr.roomrv2.Network.Callbacks;


import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observables.NetworkObservable;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class NetworkCallback implements Callback, NetworkObservable {

    protected NetworkObserver observer;
    protected JSONParser jsonParser;

    public NetworkCallback() {
        super();
        jsonParser = JSONParser.getInstance();
    }


    @Override
    public void registerObserver(NetworkObserver networkObserver) {
        this.observer = networkObserver;
    }

    @Override
    public void clearObserver() {
        this.observer = null;
    }


    @Override
    public void notifyFailure(String tag, String response) {
        this.observer.onFailure(tag, response);

    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        if(e instanceof SocketTimeoutException){
            notifyFailure("NetworkCallback", "Socket timeout error");
            return;
        }
        notifyFailure("NetworkCallback", "500: Failed to connect to server.");
    }

}
