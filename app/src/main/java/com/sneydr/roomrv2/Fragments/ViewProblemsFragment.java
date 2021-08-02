package com.sneydr.roomrv2.Fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.ProblemViewModel;
import com.sneydr.roomrv2.ViewModels.TenantViewModel;
import com.squareup.picasso.Picasso;

public class ViewProblemsFragment extends FragmentTemplate implements ProblemObserver {

    private ImageView imgProblem;
    private RadioButtonCompoundButtonInput rdgStatus;
    TextView txtCategory;
    TextView txtDescription;
    Button btnUpdate;
    int problemId;
    private String authToken;
    private int houseId;
    CardView cardView;
    private ProblemViewModel problemViewModel;



    protected void initUI(View view) {
        imgProblem = view.findViewById(R.id.imgHomeownerProblem);
        txtCategory = view.findViewById(R.id.txtHomeownerProblemType);
        txtDescription = view.findViewById(R.id.txtHomeownerProblemDescription);
        cardView = view.findViewById(R.id.crdHomeownerProblem);
        rdgStatus = new RadioButtonCompoundButtonInput(view, 0, R.id.rdgProblemStatus);
        btnUpdate = view.findViewById(R.id.btnUpdateProblem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_view_problem, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("problemId") && bundle.containsKey("authToken") && bundle.containsKey("houseId")){
            problemId = bundle.getInt("problemId");
            houseId = bundle.getInt("houseId");
            authToken = bundle.getString("authToken");
            ViewModelProviders
                .of(this)
                .get(ProblemViewModel.class)
                .getProblemById(problemId, authToken,this);
            initUI(view);
            return view;
        }
        else {
            navigation.navigateBack(this);
        }
        return view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onProblem(Problem problem) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (problem != null) {
                    Picasso.get()
                        .load(problem.getImageUrl())
                        .placeholder(R.drawable.ic_baseline_image_search_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .fit()
                        .centerCrop()
                        .into(imgProblem);
                    txtCategory.setText(problem.getCategory());
                    txtDescription.setText(problem.getDescription());
                    btnUpdate.setOnClickListener(onUpdate);
                }
            }
        });

    }

    private View.OnClickListener onUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Problem problem = new Problem(txtCategory.getText().toString(), txtDescription.getText().toString(), houseId, problemId, rdgStatus.getText());
            ViewModelProviders
                .of(ViewProblemsFragment.this)
                .get(ProblemViewModel.class)
                .updateProblem(problem, authToken, ViewProblemsFragment.this);
            animateCardSlide();
        }
    };

    private void animateCardSlide() {
        YoYo.with(Techniques.SlideOutUp).onEnd(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                navigation.navigateBack(ViewProblemsFragment.this);
            }
        }).duration(1000).playOn(cardView);
    }


}
