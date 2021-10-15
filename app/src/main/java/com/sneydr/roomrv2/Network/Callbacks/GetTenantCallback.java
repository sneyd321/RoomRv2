package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observables.TenantObservable;
import com.sneydr.roomrv2.Network.Observers.TenantObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetTenantCallback extends NetworkCallback implements TenantObservable {

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            Tenant tenant = jsonParser.parseTenant(response.body().string());
            notifyTenant(tenant);
        }
        else {
            notifyFailure("Tenant", response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyTenant(Tenant tenant) {
        TenantObserver tenantObserver = (TenantObserver) this.observer;
        tenantObserver.onTenant(tenant);
    }
}
