package com.sneydr.roomrv2.Repositories;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class HouseRepository {

    private Network network;
    private NetworkObserver observer;

    public HouseRepository(NetworkObserver observer) {
        this.network = Network.getInstance();
        this.observer = observer;
    }

    public void insert(House house) {
        Request request = network.postHouse(house);
        network.send(request, NetworkCallbackType.GetHouse, observer);
    }


    public void getHouse(int id) {
        Request request = network.getHouse(id);
        network.send(request, NetworkCallbackType.GetHouse, observer);
    }

    public void getHouses(Homeowner homeowner) {
        Request request = network.getHouses(homeowner);
        network.send(request, NetworkCallbackType.GetHouses, observer);
    }

}
