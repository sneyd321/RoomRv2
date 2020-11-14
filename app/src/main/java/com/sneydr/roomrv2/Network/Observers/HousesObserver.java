package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

public interface HousesObserver extends NetworkObserver {
    void onHouses(List<House> houses);
}
