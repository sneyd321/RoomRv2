package com.sneydr.roomrv2.Repositories;

import android.app.Application;

import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class HomeownerRepository extends Repository {



    public HomeownerRepository(Application application) {
        super(application);

    }


    public void getHomeowner(String authToken, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.getHomeowner(authToken);
            network.send(request, NetworkCallbackType.GetHomeowner, observer);
        }
    }

    public void loginHomeowner(Login login,  NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)){
            Request request = network.loginHomeowner(login);
            network.send(request, NetworkCallbackType.GetHomeowner, observer);
        }
    }
}
