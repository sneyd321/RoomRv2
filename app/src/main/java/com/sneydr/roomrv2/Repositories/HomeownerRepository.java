package com.sneydr.roomrv2.Repositories;

import android.util.Log;

import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class HomeownerRepository {


    private Network network;
    private NetworkObserver observer;


    public HomeownerRepository(NetworkObserver observer) {
        this.network = Network.getInstance();
        this.observer = observer;
    }
    public void insert(Homeowner homeowner) {
        Request request = network.postHomeowner(homeowner);
        network.send(request, NetworkCallbackType.GetHomeowner, observer);
    }

    public void getHomeowner(int id) {
        Request request = network.getHomeowner(id);
        network.send(request, NetworkCallbackType.GetHomeowner, observer);
    }

    public void loginHomeowner(Login login) {
        Request request = network.loginHomeowner(login);
        network.send(request, NetworkCallbackType.GetHomeowner, observer);
    }








}
