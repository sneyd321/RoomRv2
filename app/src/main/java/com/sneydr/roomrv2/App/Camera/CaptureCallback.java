package com.sneydr.roomrv2.App.Camera;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class CaptureCallback implements ImageCapture.OnImageSavedCallback, CameraObservable {

    private List<CameraObserver> observers;

    public CaptureCallback() {
        observers = new ArrayList<>();
    }



    @Override
    public void registerObserver(CameraObserver observer) {
        observers.add(observer);
    }

    @Override
    public void clearObserver() {
        observers.clear();
    }

    @Override
    public void notifySuccess(@NonNull ImageCapture.OutputFileResults outputFileResults) {
        for (CameraObserver observer : observers) {
            observer.onCameraSuccess(outputFileResults);
        }
    }

    @Override
    public void notifyFailure(@NonNull ImageCaptureException exception) {
        for (CameraObserver observer : observers) {
            observer.onCameraFailure(exception);
        }
    }

    @Override
    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                notifySuccess(outputFileResults);
            }
        });
    }

    @Override
    public void onError(@NonNull ImageCaptureException exception) {
        notifyFailure(exception);
    }

}
