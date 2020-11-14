package com.sneydr.roomrv2.Fragments.Tenant;

import android.animation.Animator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.navigation.fragment.NavHostFragment;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.DescriptionTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Fragments.FragmentTemplate;
import com.sneydr.roomrv2.Network.Observables.ProblemObservable;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.ProblemRepository;
import com.sneydr.roomrv2.Repositories.TenantRepository;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class AddProblemFragment extends FragmentTemplate implements ProblemObserver {


    RadioButtonCompoundButtonInput problemType;
    TextInput description;
    Button btnBack;
    Button btnReport;
    ImageView imgProblem;
    File file;
    List<TextInput> textInputList;
    CardView cardView;
    private int houseId;
    private int tenantId;
    private ProblemRepository problemRepository;





    @Override
    protected void initUI(View view) {
        textInputList = new ArrayList<>();
        problemRepository = new ProblemRepository(this);
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
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("tenantId") && bundle.containsKey("houseId")){
            tenantId = bundle.getInt("tenantId");
            houseId = bundle.getInt("houseId");
            initUI(view);
        }
        initUI(view);
        return view;
    }


    private Problem getProblem() {
        return new Problem(problemType.getText(), description.getText(), 1);
    }

    private View.OnClickListener onReport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            problemRepository.insert(getProblem(), file);
            YoYo.with(Techniques.SlideOutUp).onEnd(new YoYo.AnimatorCallback() {
                @Override
                public void call(Animator animator) {
                    if (houseId == 0) {
                        onFailure("Error loading house identifier");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("tenantId", tenantId);
                    bundle.putInt("houseId", houseId);
                    NavHostFragment.findNavController(AddProblemFragment.this)
                            .navigate(R.id.action_addProblemFragment_to_tenantStatePagerFragment, bundle);
                }
            }).duration(1000).playOn(cardView);
        }
    };


    @Override
    public void onProblems(List<Problem> problems) {

    }
}
