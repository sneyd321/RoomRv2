package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Network.Observables.NetworkObservable;

public interface SignUpRequestObserver extends NetworkObserver {

    void onSignUpRequest(String url);

}
