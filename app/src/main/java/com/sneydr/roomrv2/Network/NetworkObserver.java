package com.sneydr.roomrv2.Network;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public interface NetworkObserver {

    void onHomeowner(Homeowner homeowner);

    void onHouses(List<House> houses);

    void onTenants(List<Tenant> tenants);

    void onHouse(House house);

    void onTenant(Tenant tenant);

    void onFailure(String response);

}
