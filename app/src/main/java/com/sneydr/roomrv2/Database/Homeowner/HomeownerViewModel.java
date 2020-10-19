package com.sneydr.roomrv2.Database.Homeowner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sneydr.roomrv2.Database.Homeowner.HomeownerRepository;
import com.sneydr.roomrv2.Entities.Users.Homeowner;

public class HomeownerViewModel extends AndroidViewModel {

    private HomeownerRepository repository;
    private LiveData<Homeowner> homeowner;

    public HomeownerViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeownerRepository(application);
        homeowner = repository.getHomeowner();
    }

    public LiveData<Homeowner> getHomeowner() {
        return homeowner;
    }

}
