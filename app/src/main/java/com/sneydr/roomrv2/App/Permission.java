package com.sneydr.roomrv2.App;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permission {

    private Context context;

    public static final int INTERNET_PERMISSION_REQUEST_CODE = 1;
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 2;
    public static final int CAMERA_REQUEST_CODE = 3;


    public Permission(Context context) {
        this.context = context;
    }


    private AlertDialog buildAlertDialog(String title, String rational, final String manifestPermission, final int requestCode) {
        return new AlertDialog.Builder(this.context)
                .setTitle(title)
                .setMessage(rational)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[] {manifestPermission}, requestCode);
                    }
                })
                .setNegativeButton("Don't Allow", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }


    public boolean doesHaveInternetPermission() {
        return ContextCompat.checkSelfPermission(this.context, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean doesHaveWritePermission() {
        return ContextCompat.checkSelfPermission(this.context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean doesHaveCameraPermission() {
        return ContextCompat.checkSelfPermission(this.context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestInternetPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.INTERNET)) {
            buildAlertDialog("Internet permission", "Internet is required to access user resources.", Manifest.permission.INTERNET, INTERNET_PERMISSION_REQUEST_CODE).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.INTERNET}, INTERNET_PERMISSION_REQUEST_CODE);
        }
    }

    public void requestWriteExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            buildAlertDialog("External Storage permission", "Write access is required to save PDF to device.", Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_REQUEST_CODE).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    public void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
            buildAlertDialog("Camera permission", "Camera access is required to take a picture od problem", Manifest.permission.CAMERA, CAMERA_REQUEST_CODE).show();
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

}
