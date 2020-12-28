package com.sneydr.roomrv2.Repositories;

import android.app.Application;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.HouseObserver;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class HouseRepository extends Repository {

    public HouseRepository(Application application) {
        super(application);
    }

    public void insert(House house, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.postHouse(house);
            network.send(request, NetworkCallbackType.GetHouse, observer);
        }
    }


    private void getHouse(int id, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            //Request request = network.getHouse(id);
            //network.send(request, NetworkCallbackType.GetHouse, observer);
        }
    }

    public void getHouses(String homeownerId, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.getHouses(homeownerId);
            network.send(request, NetworkCallbackType.GetHouses, observer);
        }
    }

}
