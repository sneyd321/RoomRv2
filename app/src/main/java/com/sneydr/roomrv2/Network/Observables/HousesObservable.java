package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

public interface HousesObservable extends NetworkObservable {

    void notifyHouses(List<House> houses);


}
