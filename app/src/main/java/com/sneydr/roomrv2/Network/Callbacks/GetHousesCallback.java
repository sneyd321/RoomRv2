package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Network.Observables.HousesObservable;
import com.sneydr.roomrv2.Network.Observers.HouseObserver;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetHousesCallback extends NetworkCallback implements HousesObservable {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            List<House> houses = jsonParser.parseHouses(response.body().string());
            notifyHouses(houses);
        }
        else if (response.code() == 404) {
            notifyHouses(new ArrayList<>());
        }
        else {
            notifyFailure(response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyHouses(List<House> houses) {
        HousesObserver housesObserver = (HousesObserver) this.observer;
        housesObserver.onHouses(houses);
    }
}
