package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.util.List;

public interface TenantObserver extends NetworkObserver {



    void onTenant(Tenant tenant);
}
