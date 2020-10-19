package com.sneydr.roomrv2.Database.House;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sneydr.roomrv2.Database.House.HouseRepository;
import com.sneydr.roomrv2.Entities.House.House;

import java.util.List;

public class HouseViewModel extends AndroidViewModel {

    private HouseRepository houseRepository;
    private MutableLiveData<House> selected;


    public HouseViewModel(@NonNull Application application) {
        super(application);
        houseRepository = new HouseRepository(application);
        selected = new MutableLiveData<>();

    }

    public LiveData<House> getHouse(int houseId) {
        return houseRepository.getHouse(houseId);
    }

    public LiveData<List<House>> getHouses() {
        return houseRepository.getHouses();
    }

    public void setSelected(House house) {
        selected.setValue(house);
    }

    public MutableLiveData<House> getSelected() {
        return selected;
    }





}
