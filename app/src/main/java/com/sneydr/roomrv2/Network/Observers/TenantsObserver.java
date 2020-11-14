package com.sneydr.roomrv2.Network.Observers;


import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public interface TenantsObserver extends NetworkObserver {
    void onTenants(List<Tenant> tenants);
}
