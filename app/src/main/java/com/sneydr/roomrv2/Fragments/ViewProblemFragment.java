package com.sneydr.roomrv2.Fragments;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.RentDetails.CalendarHandler;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.ProblemRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.Request;

public class ViewProblemFragment extends FragmentTemplate implements ProblemObserver {

    private ImageView imgProblem;
    private RadioButtonCompoundButtonInput rdgStatus;
    TextView txtCategory;
    TextView txtDescription;
    Button btnUpdate;
    int problemId;
    private CalendarHandler handler;
    CardView cardView;
    private ProblemRepository problemRepository;


    @Override
    protected void initUI(View view) {
        imgProblem = view.findViewById(R.id.imgHomeownerProblem);
        txtCategory = view.findViewById(R.id.txtHomeownerProblemType);
        txtDescription = view.findViewById(R.id.txtHomeownerProblemDescription);
        cardView = view.findViewById(R.id.crdHomeownerProblem);
        rdgStatus = new RadioButtonCompoundButtonInput(view, 0, R.id.rdgProblemStatus);
        btnUpdate = view.findViewById(R.id.btnUpdateProblem);
        handler = new CalendarHandler();
        problemRepository = new ProblemRepository(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_view_problem, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("problemId")){
            problemId = bundle.getInt("problemId");
            initUI(view);
            return view;
        }
        getActivity().onBackPressed();
        return view;
    }

    View.OnClickListener onUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    @Override
    public void onProblems(List<Problem> problems) {
        Problem problem = problems.get(0);
        Picasso.get().load(problem.getImageUrl()).into(imgProblem);
        txtCategory.setText(problem.getCategory());
        txtDescription.setText(problem.getDescription());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problem.setStatus(rdgStatus.getText());
                problem.setLastUpdated(handler.getNow());
                problemRepository.update(problem);
                YoYo.with(Techniques.SlideOutUp).onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        NavHostFragment.findNavController(ViewProblemFragment.this).navigate(R.id.action_viewProblemFragment_to_housesFragment);
                    }
                }).duration(1000).playOn(cardView);
            }
        });
    }
}
