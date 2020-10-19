package com.sneydr.roomrv2.Database.Tenant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public class TenantViewModel extends AndroidViewModel {


    private TenantRepository tenantRepository;

    public TenantViewModel(@NonNull Application application) {
        super(application);
        tenantRepository = new TenantRepository(application);
    }

    public LiveData<List<Tenant>> getTenants() {
        return tenantRepository.getTenants();
    }


    public LiveData<List<Tenant>> getTenantsByHouseId(int id) {
        return tenantRepository.getTenantsByHouseId(id);
    }

    public LiveData<Tenant> getTenant(int id) {
        return tenantRepository.getTenant(id);
    }
}
