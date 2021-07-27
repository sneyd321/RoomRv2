package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.Observables.AddHouseURLObservable;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.Network.Observers.SignUpRequestObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class GetAddHouseURLCallback extends NetworkCallback implements AddHouseURLObservable {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return;
        }
        if (response.isSuccessful()) {
            if (response.code() == 204) {
                notifyFormCompete("FormComplete");
                return;
            }
            notifyObserver(responseBody.string());
        }

        else {
            notifyFailure("Add House URL", responseBody.string());
        }
        response.close();
    }

    @Override
    public void notifyObserver(String url) {
        AddHouseURLObserver addHouseURLObserver = (AddHouseURLObserver) this.observer;
        addHouseURLObserver.onAddHouseRequest(url);
    }

    @Override
    public void notifyFormCompete(String message) {
        AddHouseURLObserver addHouseURLObserver = (AddHouseURLObserver) this.observer;
        addHouseURLObserver.onFormComplete(message);
    }
}
