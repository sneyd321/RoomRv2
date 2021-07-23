package com.sneydr.roomrv2.Services;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.sneydr.roomrv2.App.NotificationHelper;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.R;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class NotificationJobService extends JobService implements NotificationObservable {

    private NotificationObserver observer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        doBackgroundWork(params);
        return true;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference docRef = db.collection("Homeowner 1").document("ProfilePicture");
                docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            Map<String, Object> document = snapshot.getData();
                            NotificationHelper notificationHelper = new NotificationHelper(NotificationJobService.this);
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            if (document.get("state").toString().equals("COMPLETE")) {

                                return;
                            }
                            switch (document.get("state").toString()) {
                                case "PENDING":
                                    notificationHelper.setContentTitle(document.get("notificationName").toString());
                                    notificationHelper.setContentText("Uploading Profile Picture...");
                                    notificationHelper.setSmallIcon(R.drawable.ic_add_a_photo_black_24dp);
                                    notificationHelper.setIndeterminateProgress();
                                    notificationHelper.setPriority(NotificationCompat.PRIORITY_LOW);
                                    notificationHelper.build();
                                    break;
                                case "SUCCESS":

                                    notificationHelper.setContentTitle(document.get("notificationName").toString());
                                    notificationHelper.setContentText("Profile Picture Uploaded Successfully");
                                    notificationHelper.setSmallIcon(R.drawable.ic_add_a_photo_black_24dp);
                                    notificationHelper.setPriority(NotificationCompat.PRIORITY_LOW);
                                    notificationHelper.build();


                                    break;
                                case "FAILURE":

                                    notificationHelper.setContentTitle(document.get("notificationName").toString());
                                    notificationHelper.setContentText("Failed to upload");
                                    notificationHelper.setSmallIcon(R.drawable.ic_add_a_photo_black_24dp);
                                    notificationHelper.setPriority(NotificationCompat.PRIORITY_LOW);
                                    notificationHelper.build();
                                    break;

                            }



                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
            }
        }).start();
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }


    @Override
    public void registerObserver(NotificationObserver notificationObserver) {
        this.observer = notificationObserver;
    }

    @Override
    public void clearObserver() {
        this.observer = null;
    }

    @Override
    public void notifyFailure(String tag, String response) {
        if (this.observer != null) {
            this.observer.onFailure(tag, response);
        }
    }

    @Override
    public void notifySuccess(String data) {
        if (this.observer != null) {
            this.observer.onNotification(data);
        }
    }
}
