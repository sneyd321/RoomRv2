package com.sneydr.roomrv2.Network.Callbacks;

public class NetworkCallbackFactory {


    public NetworkCallback getOkHttpCallback(NetworkCallbackType type) {
        switch (type) {
            case GetHouses:
               return new GetHousesCallback();
            case GetHomeowner:
                return new GetHomeownerCallback();
            case GetTenant:
                return new GetTenantCallback();
            case GetHouse:
                return new GetHouseCallback();
            case GetTenants:
                return new GetTenantsCallback();
            case EmptyCallback:
                return new NetworkCallback();
            default:
                return null;
        }

    }

}
