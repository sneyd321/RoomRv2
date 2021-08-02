package com.sneydr.roomrv2.App.Thread;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.sneydr.roomrv2.Fragments.HousesFragment;
import com.sneydr.roomrv2.Network.Observers.ActivityObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;

import java.io.File;

public class OnFile implements Runnable {


    private String authToken;
    private File file;
    private Fragment fragment;

    public OnFile(Fragment fragment, String authToken, File file) {
        this.fragment = fragment;
        this.authToken = authToken;
        this.file = file;
    }

    @Override
    public void run() {

    }
}
