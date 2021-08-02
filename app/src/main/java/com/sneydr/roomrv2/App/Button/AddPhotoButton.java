package com.sneydr.roomrv2.App.Button;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.App.Constants;
import com.sneydr.roomrv2.App.IntentFactory;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Fragments.HousesFragment;
import com.sneydr.roomrv2.Network.Observers.ActivityObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Services.NotificationService;

import static android.content.ContentValues.TAG;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class AddPhotoButton implements View.OnClickListener {

    private Activity activity;
    private IntentFactory intentFactory;
    private Fragment fragment;
    private Homeowner homeowner;


    public AddPhotoButton(Fragment fragment, Activity activity, Homeowner homeowner) {
        this.fragment = fragment;
        this.activity = activity;
        this.homeowner = homeowner;
        this.intentFactory = new IntentFactory();
    }


    @Override
    public void onClick(View v) {
        MainActivityLandlord activity = (MainActivityLandlord) this.activity;
        if (activity != null) {
            activity.registerObserver((NetworkObserver) fragment);
            activity.startActivityForResult(intentFactory.getGalleryIntent(), Constants.IMAGE_INTENT);
            scheduleJob(homeowner.getEmail(), "ProfilePicture");

        }
        else {
            Toast.makeText(fragment.getContext(), "An Unexpected Error Occurred. Activity Was Null.", Toast.LENGTH_LONG).show();
        }
    }


    protected void scheduleJob(String email, String resource) {
        if (activity == null) return;
        ComponentName componentName = new ComponentName(activity, NotificationService.class);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("email", email);
        bundle.putString("resource", resource);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .setExtras(bundle)
                .build();
        JobScheduler scheduler = (JobScheduler) activity.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }
}
