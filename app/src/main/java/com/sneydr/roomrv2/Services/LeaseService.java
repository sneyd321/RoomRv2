package com.sneydr.roomrv2.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LeaseService extends Service {



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getStringExtra("lease") != null) {
            Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_LONG).show();



        }
        return START_NOT_STICKY;
    }











    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
