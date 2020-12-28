package com.sneydr.roomrv2.Repositories;

import android.app.Application;
import android.content.Context;

import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observables.InternetAvailableObservable;
import com.sneydr.roomrv2.Network.Observables.InternetPermissionObservable;
import com.sneydr.roomrv2.Network.Observers.InternetAvailableObserver;
import com.sneydr.roomrv2.Network.Observers.InternetPermissionObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

public abstract class Repository implements InternetAvailableObservable, InternetPermissionObservable {


    protected Network network;
    protected Application application;
    protected Permission permission;
    private NetworkObserver observer;


    public Repository(Application application) {
        this.network = Network.getInstance();
        this.application = application;
        this.permission = new Permission(application);
    }

    public boolean doesHaveInternetPermission(NetworkObserver observer) {
        if (!permission.doesHaveInternetPermission()){
            registerObserver(observer);
            notifyNoInternetPermission(permission);
            return false;
        }
        return true;
    }

    public boolean doesHaveInternet(NetworkObserver observer) {
        if (!network.isNetworkAvailable(application)) {
            registerObserver(observer);
            notifyInternetNotAvailable("Could not connect to the internet.");
            return false;
        }
        return true;
    }

    @Override
    public void notifyFailure(String response) {
        observer.onFailure(response);
    }

    @Override
    public void notifyInternetNotAvailable(String text) {
        InternetAvailableObserver internetAvailableObserver = (InternetAvailableObserver) observer;
        internetAvailableObserver.onNoInternet(text);
    }

    @Override
    public void notifyNoInternetPermission(Permission permission) {
        InternetPermissionObserver internetPermissionObserver = (InternetPermissionObserver) observer;
        internetPermissionObserver.onNoInternetPermission(permission);
    }

    @Override
    public void registerObserver(NetworkObserver networkObserver) {
        this.observer = networkObserver;
    }

    @Override
    public void clearObserver() {
        this.observer = null;
    }
}
