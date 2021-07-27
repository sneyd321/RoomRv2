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

import static com.sneydr.roomrv2.App.Constants.SERVER_URL;

public class HouseRepository extends Repository {

    public HouseRepository(Application application) {
        super(application);
    }

    public void getHouses(String homeownerId, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.getHouses(homeownerId);
            network.send(request, NetworkCallbackType.GetHouses, observer);
        }
    }

    public void getHouseURL(String authToken, String url, NetworkObserver observer) {
        if (network.isNetworkAvailable(application)){
            Request request = network.getURL(url, authToken);
            network.send(request, NetworkCallbackType.GetAddHouseURL, observer);
        }
    }

}
