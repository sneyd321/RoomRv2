package com.sneydr.roomrv2.App.Camera;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;

public interface CameraObserver {

    void onCameraSuccess(@NonNull ImageCapture.OutputFileResults outputFileResults);

    void onCameraFailure(@NonNull ImageCaptureException exception);


}
