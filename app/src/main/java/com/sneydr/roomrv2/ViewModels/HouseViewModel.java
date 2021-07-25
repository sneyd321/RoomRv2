package com.sneydr.roomrv2.ViewModels;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;

import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.Network.Observers.HouseObserver;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;
import com.sneydr.roomrv2.Repositories.HouseRepository;

import java.util.List;

public class HouseViewModel extends AndroidViewModel implements LifecycleObserver {


    HouseRepository repository;


    public HouseViewModel(@NonNull Application application) {
        super(application);
        repository = new HouseRepository(application);

    }



    private void getHouse(int houseId, HouseObserver observer) {
        //repository.getHouse(houseId, observer);
    }

    public void getHouses(String homeownerId, HousesObserver observer) {
        repository.getHouses(homeownerId, observer);
    }

    public void getHouseURL(String authToken, AddHouseURLObserver observer) {
        repository.getHouseURL(authToken, observer);
    }




}
