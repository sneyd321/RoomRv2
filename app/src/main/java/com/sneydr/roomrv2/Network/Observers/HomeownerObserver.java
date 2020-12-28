package com.sneydr.roomrv2.Network.Observers;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.sneydr.roomrv2.Entities.Users.Homeowner;

public interface HomeownerObserver extends NetworkObserver {

    void onHomeowner(Homeowner homeowner);

}
