package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

public class NetworkCallbackFactory {


    public NetworkCallback getNetworkCallback(NetworkCallbackType type, NetworkObserver observer) {
        NetworkCallback callback = null;
        switch (type) {
            case GetHouses:
                callback = new GetHousesCallback();
                break;
            case GetHomeowner:
                callback = new GetHomeownerCallback();
                break;
            case GetTenant:
                callback = new GetTenantCallback();
                break;
            case GetHouse:
                callback = new GetHouseCallback();
                break;
            case GetTenants:
                callback = new GetTenantsCallback();
                break;
            case GetProblems:
                callback = new GetProblemsCallback();
                break;
            case GetProblem:
                callback = new GetProblemCallback();
                break;
            case GetLease:
                callback = new GetLeaseCallback();
                break;
            case GetSignUpURL:
                callback = new GetSignUpURLCallback();
                break;
        }
        if (callback != null) {
            callback.registerObserver(observer);
        }
        return callback;

    }

}
