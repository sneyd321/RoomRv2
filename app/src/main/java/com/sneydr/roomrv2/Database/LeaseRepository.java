package com.sneydr.roomrv2.Database;

import android.app.Application;
import android.os.AsyncTask;

import com.sneydr.roomrv2.Database.Homeowner.HomeownerDao;
import com.sneydr.roomrv2.Database.House.HouseDao;
import com.sneydr.roomrv2.Database.Tenant.TenantDao;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LeaseRepository {

    private HomeownerDao homeownerDao;
    private HouseDao houseDao;
    private TenantDao tenantDao;


    public LeaseRepository(Application application) {
        RoomRDatabase database = RoomRDatabase.getInstance(application);
        homeownerDao = database.homeownerDao();
        houseDao = database.houseDao();
        tenantDao = database.tenantDao();
    }

    public Lease getLeaseData(int houseId)  {
        GetLease getLeaseTask = new GetLease(homeownerDao, houseDao, tenantDao);
        try {
            return getLeaseTask.execute(houseId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetLease extends AsyncTask<Integer, Void, Lease> {
        private HomeownerDao homeownerDao;
        private HouseDao houseDao;
        private TenantDao tenantDao;

        public GetLease(HomeownerDao homeownerDao, HouseDao houseDao, TenantDao tenantDao) {
            this.homeownerDao = homeownerDao;
            this.houseDao = houseDao;
            this.tenantDao = tenantDao;
        }


        @Override
        protected Lease doInBackground(Integer... integers) {
            House house = houseDao.getHouseForLease(integers[0]);
            Homeowner homeowner = homeownerDao.getHomeownerForLease(house.getHomeownerId());
            List<Tenant> tenants = tenantDao.getTenantsByHouseIdForLease(house.getHouseId());
            return new Lease(house, homeowner, tenants);
        }
    }


}
