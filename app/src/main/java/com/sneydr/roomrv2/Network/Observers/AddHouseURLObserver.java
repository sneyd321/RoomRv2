package com.sneydr.roomrv2.Network.Observers;


public interface AddHouseURLObserver extends NetworkObserver {

    void onAddHouseRequest(String url);

    void onFormComplete(String message);
}
