package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

public interface HouseObservable extends NetworkObservable {



    void notifyHouse(House house);
}
