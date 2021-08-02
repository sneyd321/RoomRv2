package com.sneydr.roomrv2.Fragments;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.App.Naviagation.Navigation;
import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.App.IntentFactory;
import com.sneydr.roomrv2.Network.Observers.InternetAvailableObserver;
import com.sneydr.roomrv2.Network.Observers.InternetPermissionObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Services.NotificationService;

import static android.content.ContentValues.TAG;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public abstract class FragmentTemplate extends Fragment implements NetworkObserver, LifecycleOwner, InternetPermissionObserver, InternetAvailableObserver {


    protected Handler handler;
    protected Context context;
    protected String authToken;
    protected int houseId;
    protected IntentFactory intentFactory;
    protected Permission permission;
    protected Navigation navigation;
    protected String email;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        handler = new Handler(Looper.getMainLooper());
        intentFactory = new IntentFactory();
        this.permission = new Permission(context);
        this.navigation = Navigation.getInstance();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onNoInternetPermission(Permission permission) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                permission.requestInternetPermission();
            }
        });
    }

    @Override
    public void onNoInternet(String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new Dialog(context);
                dialog.setMessage(text);
                dialog.buildErrorDialog().show();
            }
        });

    }

    @Override
    public void onFailure(String tag, String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public FragmentTemplate setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public FragmentTemplate setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public FragmentTemplate setEmail(String email) {
        this.email = email;
        return this;
    }

    protected void scheduleJob(String email, String resource) {
        Activity activity = getActivity();
        if (activity == null) return;
        ComponentName componentName = new ComponentName(getActivity(), NotificationService.class);
        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("email", email);
        bundle.putString("resource", resource);
        JobInfo info = new JobInfo.Builder(124, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .setExtras(bundle)
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }




}
