package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Network.Observables.HouseObservable;
import com.sneydr.roomrv2.Network.Observables.NetworkObservable;
import com.sneydr.roomrv2.Network.Observers.HouseObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetHouseCallback extends NetworkCallback implements HouseObservable {

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            String body = response.body().string();
            if (body.isEmpty()) {
                House house = jsonParser.parseHouse(body);
                notifyHouse(house);
            }
            else {
                notifyHouse(null);
            }


        }
        else {
            notifyFailure("House", response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyHouse(House house) {
        HouseObserver houseObserver = (HouseObserver) this.observer;
        houseObserver.onHouse(house);
    }
}
