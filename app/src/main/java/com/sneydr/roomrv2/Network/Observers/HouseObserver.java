package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

public interface HouseObserver extends NetworkObserver {



    void onHouse(House house);
}
