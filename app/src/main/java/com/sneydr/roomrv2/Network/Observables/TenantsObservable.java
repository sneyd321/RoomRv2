package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public interface TenantsObservable extends NetworkObservable {

    void notifyTenants(List<Tenant> tenants);
}
