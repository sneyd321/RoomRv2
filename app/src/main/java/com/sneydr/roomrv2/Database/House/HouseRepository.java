package com.sneydr.roomrv2.Database.House;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sneydr.roomrv2.Database.RoomRDatabase;
import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

public class HouseRepository {

    private HouseDao houseDao;


    public HouseRepository(Application application) {
        RoomRDatabase database = RoomRDatabase.getInstance(application);
        houseDao = database.houseDao();
    }
    public void insert(House house) {
        new HouseRepository.InsertHouseAsyncTask(houseDao).execute(house);
    }
    public void update(House house) {
        new HouseRepository.UpdateHouseAsyncTask(houseDao).execute(house);
    }
    public void delete(House house) {
        new HouseRepository.DeleteHouseAsyncTask(houseDao).execute(house);
    }
    public LiveData<House> getHouse(int id) {
        return houseDao.getHouse(id);
    }

    public LiveData<List<House>> getHouses() {
        return houseDao.getHouses();
    }



    private static class InsertHouseAsyncTask extends AsyncTask<House, Void, Void> {
        private HouseDao houseDao;
        private InsertHouseAsyncTask(HouseDao houseDao) {
            this.houseDao = houseDao;
        }

        @Override
        protected Void doInBackground(House... houses) {
            houseDao.insert(houses[0]);
            return null;
        }
    }
    private static class UpdateHouseAsyncTask extends AsyncTask<House, Void, Void> {
        private HouseDao houseDao;
        private UpdateHouseAsyncTask(HouseDao houseDao) {
            this.houseDao = houseDao;
        }

        @Override
        protected Void doInBackground(House... houses) {
            houseDao.update(houses[0]);
            return null;
        }
    }
    private static class DeleteHouseAsyncTask extends AsyncTask<House, Void, Void> {
        private HouseDao houseDao;
        private DeleteHouseAsyncTask(HouseDao houseDao) {
            this.houseDao = houseDao;
        }

        @Override
        protected Void doInBackground(House... houses) {
            houseDao.delete(houses[0]);
            return null;
        }
    }

}
