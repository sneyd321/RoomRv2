package com.sneydr.roomrv2.Fragments;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.common.util.concurrent.ListenableFuture;
import com.sneydr.roomrv2.Adapters.ButtonState;
import com.sneydr.roomrv2.App.Camera.Camera;
import com.sneydr.roomrv2.App.Camera.CameraObserver;
import com.sneydr.roomrv2.App.Camera.CaptureCallback;
import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.DescriptionTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Callbacks.GetHomeownerCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.Request;

public class AddProblemFragment extends FragmentTemplate {


    RadioButtonCompoundButtonInput problemType;
    TextInput description;
    Button btnBack;
    Button btnReport;
    ImageView imgProblem;
    File file;
    List<TextInput> textInputList;
    CardView cardView;



    @Override
    protected void initUI(View view) {
        textInputList = new ArrayList<>();
        cardView = view.findViewById(R.id.crdAddProblem);
        problemType = new RadioButtonCompoundButtonInput(view, 0 , R.id.rdgProblemType);
        description = new DescriptionTextInput(view, R.id.tilAddProblemDescription, R.id.edtAddProblemDescription);
        textInputList.add(description);
        btnBack = view.findViewById(R.id.btnAddProblemBack);
        btnReport = view.findViewById(R.id.btnAddProblemReportProblem);
        btnReport.setOnClickListener(onReport);
        imgProblem = view.findViewById(R.id.imgProblem);
        file = new File(context.getCacheDir(), "Problem.jpg");
        Picasso.get().load(file).memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE).into(imgProblem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_problem, container, false);
        initUI(view);
        return view;
    }


    private Problem getProblem() {

        return new Problem(problemType.getText(), description.getText(), 1);
    }

    private View.OnClickListener onReport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Request request = network.postProblem(getProblem(), file);
            send(textInputList, request, new NetworkCallback());
            YoYo.with(Techniques.SlideOutUp).onEnd(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    NavHostFragment.findNavController(AddProblemFragment.this).navigate(R.id.action_addProblemFragment_to_tenantProblemFragment);
                }
            }).duration(750).playOn(cardView);
        }
    };




}
