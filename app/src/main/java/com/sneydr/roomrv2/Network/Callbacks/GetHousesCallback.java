package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.JSONParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetHousesCallback extends NetworkCallback {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.onResponse(call, response);
        if (response.isSuccessful()){
            JSONParser jsonParser = JSONParser.getInstance();
            List<House> houses = jsonParser.parseHouses(response.body().string());
            notifyHouses(houses);
            response.close();
            return;
        }
        if (response.code() == 404) {
            notifyHouses(new ArrayList<>());
            response.close();
            return;
        }
        notifyFailure(response.body().string());
        response.close();
    }
}
