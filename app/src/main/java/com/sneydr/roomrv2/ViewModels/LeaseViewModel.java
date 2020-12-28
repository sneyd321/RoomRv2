package com.sneydr.roomrv2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Fragments.GenerateLeaseFragment;
import com.sneydr.roomrv2.Network.Observers.LeaseObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Repositories.LeaseRepository;

public class LeaseViewModel extends AndroidViewModel{

    private LeaseRepository repository;

    public LeaseViewModel(@NonNull Application application) {
        super(application);
        repository = new LeaseRepository(application);

    }


    public void sendLease(Lease lease, String authToken, LeaseObserver observer) {
        repository.sendLeaseData(lease, authToken, observer);
    }
}
