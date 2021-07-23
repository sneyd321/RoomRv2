package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.Observables.SignUpRequestObservable;
import com.sneydr.roomrv2.Network.Observers.SignUpRequestObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetSignUpURLCallback extends NetworkCallback implements SignUpRequestObservable {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

        if (response.isSuccessful()) {

            notifyObserver(response.body().string());
        }
        else {
            notifyFailure("Web", response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyObserver(String url) {
        SignUpRequestObserver signUpRequestObserver = (SignUpRequestObserver) this.observer;
        signUpRequestObserver.onSignUpRequest(url);
    }
}
