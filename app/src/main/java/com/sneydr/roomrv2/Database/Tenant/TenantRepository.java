package com.sneydr.roomrv2.Database.Tenant;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sneydr.roomrv2.Database.House.HouseDao;
import com.sneydr.roomrv2.Database.House.HouseRepository;
import com.sneydr.roomrv2.Database.RoomRDatabase;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public class TenantRepository {

    private TenantDao tenantDao;


    public TenantRepository(Application application) {
        RoomRDatabase database = RoomRDatabase.getInstance(application);
        tenantDao = database.tenantDao();
    }
    public void insert(Tenant tenant) {
        new TenantRepository.InsertTenantAsyncTask(tenantDao).execute(tenant);
    }
    public void update(Tenant tenant) {
        new TenantRepository.UpdateTenantAsyncTask(tenantDao).execute(tenant);
    }
    public void delete(Tenant tenant) {
        new TenantRepository.DeleteTenantAsyncTask(tenantDao).execute(tenant);
    }
    public LiveData<Tenant> getTenant(int id) {
        return tenantDao.getTenant(id);
    }

    public LiveData<List<Tenant>> getTenants() {
        return tenantDao.getTenants();
    }

    public LiveData<List<Tenant>> getTenantsByHouseId(int id) {
        return tenantDao.getTenantsByHouseId(id);
    }



    private static class InsertTenantAsyncTask extends AsyncTask<Tenant, Void, Void> {
        private TenantDao tenantDao;
        private InsertTenantAsyncTask(TenantDao tenantDao) {
            this.tenantDao = tenantDao;
        }

        @Override
        protected Void doInBackground(Tenant... tenants) {
            tenantDao.insert(tenants[0]);
            return null;
        }
    }

    private static class UpdateTenantAsyncTask extends AsyncTask<Tenant, Void, Void> {
        private TenantDao tenantDao;
        private UpdateTenantAsyncTask(TenantDao tenantDao) {
            this.tenantDao = tenantDao;
        }

        @Override
        protected Void doInBackground(Tenant... tenants) {
            tenantDao.insert(tenants[0]);
            return null;
        }
    }

    private static class DeleteTenantAsyncTask extends AsyncTask<Tenant, Void, Void> {
        private TenantDao tenantDao;
        private DeleteTenantAsyncTask(TenantDao tenantDao) {
            this.tenantDao = tenantDao;
        }

        @Override
        protected Void doInBackground(Tenant... tenants) {
            tenantDao.insert(tenants[0]);
            return null;
        }
    }

}
