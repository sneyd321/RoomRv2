package com.sneydr.roomrv2.Network.Observers;

import androidx.lifecycle.LifecycleObserver;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public interface NetworkObserver extends LifecycleObserver {

    void onFailure(String response);

}
