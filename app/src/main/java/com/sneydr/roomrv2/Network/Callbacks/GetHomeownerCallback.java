package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Entities.Users.Homeowner;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetHomeownerCallback extends NetworkCallback {


    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.onResponse(call, response);
        if (response.isSuccessful()){
            JSONParser jsonParser = JSONParser.getInstance();
            Homeowner homeowner = jsonParser.parseHomeowner(response.body().string());
            notifyHomeowner(homeowner);
            response.close();
            return;
        }
        notifyFailure(response.body().string());
        response.close();
    }


}
