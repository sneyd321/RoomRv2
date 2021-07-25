package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Observables.HomeownerObservable;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetHomeownerCallback extends NetworkCallback implements HomeownerObservable {



    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            notifyFailure("Homeowner", "Error: Server Returned an Empty Response");
            response.close();
            return;
        }

        if (response.isSuccessful()){
            Homeowner homeowner = jsonParser.parseHomeowner(responseBody.byteStream());
            if (homeowner != null){
                notifyHomeowner(homeowner);
            }
            else {
                notifyFailure("Homeowner", "Error: Failed to load homeowner data");
            }
        }
        else {
            notifyFailure("Homeowner", responseBody.string());
        }
        response.close();
    }

    @Override
    public void notifyHomeowner(Homeowner homeowner) {
        HomeownerObserver homeownerObserver = (HomeownerObserver) this.observer;
        homeownerObserver.onHomeowner(homeowner);
    }
}
