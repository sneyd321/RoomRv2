package com.sneydr.roomrv2.Network;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public interface NetworkObservable {

    void registerObserver(NetworkObserver networkObserver);

    void clearObserver();

    void notifyHomeowner(Homeowner homeowner);

    void notifyHouses(List<House> houses);

    void notifyHouse(House house);

    void notifyTenants(List<Tenant> tenants);

    void notifyTenant(Tenant tenant);

    void notifyFailure(String response);
}
