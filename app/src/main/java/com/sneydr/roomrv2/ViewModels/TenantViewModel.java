package com.sneydr.roomrv2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Repositories.TenantRepository;

public class TenantViewModel extends AndroidViewModel {

    TenantRepository repository;

    public TenantViewModel(@NonNull Application application) {
        super(application);
        this.repository = new TenantRepository(application);
    }


    private void saveTenant(Tenant tenant, NetworkObserver observer) {
        //repository.insert(tenant, observer);
    }

    public void updateTenant(Tenant tenant, String authToken, NetworkObserver observer) {
        repository.update(tenant, authToken, observer);
    }

    private void getTenant(int tenantId, NetworkObserver observer) {
        //repository.getTenant(tenantId, observer);
    }

    public void getTenantByHouseId(int houseId, String authToken, NetworkObserver observer) {
        repository.getTenantsByHouseId(houseId, authToken, observer);
    }




}
