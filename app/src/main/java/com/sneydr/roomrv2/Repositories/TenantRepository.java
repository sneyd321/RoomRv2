package com.sneydr.roomrv2.Repositories;

import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class TenantRepository {


    private Network network;
    private NetworkObserver observer;


    public TenantRepository(NetworkObserver observer) {
        network = Network.getInstance();
        this.observer = observer;
    }


    public void insert(Tenant tenant) {
        Request request = network.postTenant(tenant);
        network.send(request, NetworkCallbackType.GetTenant, observer);
    }

    public void update(Tenant tenant) {
        Request request = network.putTenant(tenant);
        network.send(request, NetworkCallbackType.GetTenant, observer);
    }

    public void getTenant(int id) {
        Request request = network.getTenant(id);
        network.send(request, NetworkCallbackType.GetTenant, observer);
    }

    public void getTenantsByHouseId(int houseId) {
        Request request = network.getTenants(houseId);
        network.send(request, NetworkCallbackType.GetTenants, observer);
    }

    public void loginTenant(Login login) {
        Request request = network.loginTenant(login);
        network.send(request, NetworkCallbackType.GetTenant, observer);
    }


}
