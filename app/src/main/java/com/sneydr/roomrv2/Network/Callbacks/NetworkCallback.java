package com.sneydr.roomrv2.Network.Callbacks;


import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.NetworkObservable;
import com.sneydr.roomrv2.Network.NetworkObserver;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NetworkCallback implements Callback, NetworkObservable {

    private List<NetworkObserver> observers;

    public NetworkCallback() {
        super();
        this.observers = new ArrayList<>();
    }


    @Override
    public void registerObserver(NetworkObserver networkObserver) {
        this.observers.add(networkObserver);
    }

    @Override
    public void clearObserver() {
        this.observers = new ArrayList<>();
    }


    @Override
    public void notifyFailure(String response) {
        for (NetworkObserver networkObserver : this.observers) {
            networkObserver.onFailure(response);
        }
    }

    @Override
    public void notifyHomeowner(Homeowner homeowner) {
        for (NetworkObserver networkObserver : this.observers) {
            networkObserver.onHomeowner(homeowner);
        }
    }

    @Override
    public void notifyHouses(List<House> houses) {
        for (NetworkObserver networkObserver : this.observers) {
            networkObserver.onHouses(houses);
        }
    }

    @Override
    public void notifyHouse(House house) {
        for (NetworkObserver networkObserver : this.observers) {
            networkObserver.onHouse(house);
        }
    }

    @Override
    public void notifyTenants(List<Tenant> tenants) {
        for (NetworkObserver networkObserver : this.observers) {
            networkObserver.onTenants(tenants);
        }
    }

    @Override
    public void notifyTenant(Tenant tenant) {
        for (NetworkObserver networkObserver : this.observers) {
            networkObserver.onTenant(tenant);
        }
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {
        if(e instanceof SocketTimeoutException){
            notifyFailure("Socket timeout error");
            return;
        }
        notifyFailure("500: Failed to connect to server.");
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

    }
}
