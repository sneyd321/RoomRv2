package com.sneydr.roomrv2.Fragments.Tenant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.view.PreviewView;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sneydr.roomrv2.App.Camera.Camera;
import com.sneydr.roomrv2.App.Camera.CameraObserver;
import com.sneydr.roomrv2.Fragments.FragmentTemplate;
import com.sneydr.roomrv2.R;

public class CameraFragment extends FragmentTemplate implements CameraObserver {

    PreviewView cameraPreview;
    FloatingActionButton fabCamera;
    private Camera camera;
    private int tenantId;
    private int houseId;


    @Override
    protected void initUI(View view) {
        cameraPreview = view.findViewById(R.id.cameraPreview);
        fabCamera = view.findViewById(R.id.fabTakePhoto);
        fabCamera.setOnClickListener(onCamera);
        camera = new Camera(cameraPreview);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("tenantId") && bundle.containsKey("houseId")){
            tenantId = bundle.getInt("tenantId");
            houseId = bundle.getInt("houseId");
            initUI(view);
            if (permission.doesHaveCameraPermission()) {
                camera.startCamera(getViewLifecycleOwner());
                return view;
            }
            permission.requestCameraPermission();
        }
        return view;
    }

    View.OnClickListener onCamera = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            camera.capturePicture(CameraFragment.this);
        }
    };

    @Override
    public void onCameraSuccess(@NonNull ImageCapture.OutputFileResults outputFileResults) {
        if (houseId == 0) {
            onFailure("Error loading house identifier");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("tenantId", tenantId);
        bundle.putInt("houseId", houseId);
        NavHostFragment.findNavController(this).navigate(R.id.action_cameraFragment_to_addProblemFragment, bundle);
    }

    @Override
    public void onCameraFailure(@NonNull ImageCaptureException exception) {
        Toast.makeText(context, "Camera failed to take photo.", Toast.LENGTH_SHORT).show();
    }
}
