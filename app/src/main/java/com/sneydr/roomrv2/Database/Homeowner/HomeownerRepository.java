package com.sneydr.roomrv2.Database.Homeowner;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.sneydr.roomrv2.Database.RoomRDatabase;
import com.sneydr.roomrv2.Entities.Users.Homeowner;

public class HomeownerRepository {
    private HomeownerDao homeownerDao;
    private LiveData<Homeowner> homeowner;


    public HomeownerRepository(Application application) {
        RoomRDatabase database = RoomRDatabase.getInstance(application);
        homeownerDao = database.homeownerDao();
        homeowner = homeownerDao.getHomeowner();
    }
    public void insert(Homeowner homeowner) {
        new InsertHomeownerAsyncTask(homeownerDao).execute(homeowner);
    }
    public void update(Homeowner homeowner) {
        new UpdateHomeownerAsyncTask(homeownerDao).execute(homeowner);
    }
    public void delete(Homeowner homeowner) {
        new DeleteHomeownerAsyncTask(homeownerDao).execute(homeowner);
    }
    public LiveData<Homeowner> getHomeowner() {
        return homeowner;
    }


    private static class InsertHomeownerAsyncTask extends AsyncTask<Homeowner, Void, Void> {
        private HomeownerDao homeownerDao;
        private InsertHomeownerAsyncTask(HomeownerDao homeownerDao) {
            this.homeownerDao = homeownerDao;
        }
        @Override
        protected Void doInBackground(Homeowner... homeowners) {
            homeownerDao.insert(homeowners[0]);
            return null;
        }
    }
    private static class UpdateHomeownerAsyncTask extends AsyncTask<Homeowner, Void, Void> {
        private HomeownerDao homeownerDao;
        private UpdateHomeownerAsyncTask(HomeownerDao homeownerDao) {
            this.homeownerDao = homeownerDao;
        }
        @Override
        protected Void doInBackground(Homeowner... homeowners) {
            homeownerDao.update(homeowners[0]);
            return null;
        }
    }
    private static class DeleteHomeownerAsyncTask extends AsyncTask<Homeowner, Void, Void> {
        private HomeownerDao homeownerDao;
        private DeleteHomeownerAsyncTask(HomeownerDao homeownerDao) {
            this.homeownerDao = homeownerDao;
        }
        @Override
        protected Void doInBackground(Homeowner... homeowners) {
            homeownerDao.delete(homeowners[0]);
            return null;
        }
    }





}
