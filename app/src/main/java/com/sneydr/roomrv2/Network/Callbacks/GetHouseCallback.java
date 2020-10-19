package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.JSONParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetHouseCallback extends NetworkCallback {

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.onResponse(call, response);
        if (response.isSuccessful()){
            JSONParser jsonParser = JSONParser.getInstance();
            House house = jsonParser.parseHouse(response.body().string());
            notifyHouse(house);
            response.close();
            return;
        }
        notifyFailure(response.body().string());
        response.close();
    }
}
