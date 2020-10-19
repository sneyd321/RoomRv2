package com.sneydr.roomrv2.App.Camera;

import android.content.Context;
import android.view.WindowManager;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Camera {

    private Preview preview;
    private CameraSelector cameraSelector;
    private ImageAnalysis imageAnalysis;
    private ImageCapture imageCapture;
    private Context context;
    private Executor executor = Executors.newSingleThreadExecutor();

    public Camera(PreviewView previewView) {
        this.context = previewView.getContext();
        preview = buildPreview(previewView.getSurfaceProvider());
        cameraSelector = buildCameraSelector();
        imageAnalysis = buildImageAnalysis();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation =  windowManager.getDefaultDisplay().getRotation();
        imageCapture = buildImageCapture(cameraSelector, rotation);
    }


    private Preview buildPreview(Preview.SurfaceProvider surfaceProvider) {
        Preview preview = new Preview.Builder()
                .build();
        preview.setSurfaceProvider(surfaceProvider);
        return preview;
    }

    public Preview getPreview() {
        return preview;
    }

    private CameraSelector buildCameraSelector() {
        return new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
    }

    public CameraSelector getCameraSelector() {
        return cameraSelector;
    }

    private ImageAnalysis buildImageAnalysis() {
        return new ImageAnalysis.Builder()
                .build();
    }

    public ImageAnalysis getImageAnalysis() {
        return imageAnalysis;
    }

    private ImageCapture buildImageCapture(CameraSelector cameraSelector, int rotation) {
        ImageCapture.Builder builder = new ImageCapture.Builder();
        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }
        return builder
                .setTargetRotation(rotation)
                .build();
    }

    public ImageCapture getImageCapture() {
        return imageCapture;
    }

    public void capturePicture(CameraObserver cameraObserver) {
        File file = new File(context.getCacheDir(), "Problem.jpg");
        file.delete();
        file = new File(context.getCacheDir(), "Problem.jpg");
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        CaptureCallback captureCallback = new CaptureCallback();
        captureCallback.registerObserver(cameraObserver);
        imageCapture.takePicture(outputFileOptions, executor, captureCallback);
    }

    public void startCamera(LifecycleOwner lifecycleOwner) {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(context);
        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    cameraProvider.bindToLifecycle(lifecycleOwner, getCameraSelector(), getPreview(), getImageAnalysis(), getImageCapture());

                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(context));

    }


}
