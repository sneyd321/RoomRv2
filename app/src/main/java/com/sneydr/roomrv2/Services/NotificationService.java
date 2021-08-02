package com.sneydr.roomrv2.Services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;

import java.util.ArrayList;

public class NotificationService extends JobService  {


    @Override
    public boolean onStartJob(JobParameters params) {
        if (params.getExtras().containsKey("email") && params.getExtras().containsKey("resource")){
            doBackgroundWork(params);
            return true;
        }
        onStopJob(params);
        return false;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference docRef = db
                        .collection("Homeowner " + params.getExtras().getString("email"))
                        .document(params.getExtras().getString("resource"));
                docRef.addSnapshotListener(new NotificationEventListener(NotificationService.this));
            }
        }).start();
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }


}
