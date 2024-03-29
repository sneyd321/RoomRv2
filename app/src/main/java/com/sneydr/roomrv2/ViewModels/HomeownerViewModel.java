package com.sneydr.roomrv2.ViewModels;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.Network.Observers.EmptyObserver;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.HomeownerRepository;
import com.sneydr.roomrv2.Repositories.HouseRepository;

import java.io.File;


public class HomeownerViewModel extends AndroidViewModel  {

    HomeownerRepository repository;

    public HomeownerViewModel(Application application) {
        super(application);
        repository = new HomeownerRepository(application);
    }

    public void loadHomeowner(String authToken, HomeownerObserver observer) {
        repository.getHomeowner(authToken, observer);
    }

    public void login(Login login, HomeownerObserver observer) {
        repository.loginHomeowner(login, observer);
    }


    public void uploadHomeownerProfile(String authToken, File file, NetworkObserver observer) {
        repository.uploadHomeownerImage(authToken, file, observer);
    }

    public void getSignInUrl(String authToken, String url, AddHouseURLObserver observer) {
        repository.getSignInUrl(authToken, url, observer);
    }

}
