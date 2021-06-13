package com.sneydr.roomrv2.Repositories;

import android.app.Application;


import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class TenantRepository extends Repository {


    public TenantRepository(Application application) {
        super(application);
    }



    public void update(Tenant tenant, String authToken, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.putTenant(tenant, authToken);
            network.send(request, NetworkCallbackType.GetTenants, observer);
        }
    }

    private void getTenant(int id, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            //Request request = network.getTenant(id);
            //network.send(request, NetworkCallbackType.GetTenant, observer);
        }
    }

    public void getTenantsByHouseId(int houseId, String authToken, NetworkObserver observer) {
        //if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
        Request request = network.getTenants(houseId, authToken);
        network.send(request, NetworkCallbackType.GetTenants, observer);
        //}

    }





}
