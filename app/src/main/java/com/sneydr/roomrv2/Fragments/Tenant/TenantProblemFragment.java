package com.sneydr.roomrv2.Fragments.Tenant;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sneydr.roomrv2.Adapters.ProblemsRecyclerViewAdapter;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Fragments.FragmentTemplate;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;
import com.sneydr.roomrv2.Network.Observers.TenantObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.ProblemRepository;
import com.sneydr.roomrv2.Repositories.TenantRepository;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class TenantProblemFragment extends FragmentTemplate implements TenantObserver, ProblemObserver {


    private RecyclerView rcyProblems;
    private ProblemsRecyclerViewAdapter adapter;
    private FloatingActionButton btnAddProblem;
    private int tenantId;
    private int houseId;
    private ProblemRepository problemRepository;
    private TenantRepository tenantRepository;


    @Override
    protected void initUI(View view) {
        problemRepository = new ProblemRepository(this);
        tenantRepository = new TenantRepository(this);
        tenantRepository.getTenant(tenantId);
        rcyProblems = view.findViewById(R.id.rcyTenantProblems);
        btnAddProblem = view.findViewById(R.id.fabAddProblem);
        btnAddProblem.setOnClickListener(onAddProblem);
        adapter = new ProblemsRecyclerViewAdapter(context, new ArrayList<>());
        rcyProblems.setLayoutManager(new LinearLayoutManager(context));
        rcyProblems.setAdapter(adapter);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tenant_problem, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onTenant(Tenant tenant) {
        houseId = tenant.getHouseId();
        problemRepository.getProblemByHouseId(houseId);
    }


    @Override
    public void onProblems(List<Problem> problems) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (problems.isEmpty()){
                    rcyProblems.setLayoutManager(new LinearLayoutManager(context));
                    adapter = new ProblemsRecyclerViewAdapter(context, problems);
                    rcyProblems.swapAdapter(adapter, false);
                    return;
                }
                rcyProblems.setLayoutManager(new GridLayoutManager(context, 2));
                adapter = new ProblemsRecyclerViewAdapter(context, problems);
                rcyProblems.swapAdapter(adapter, false);
            }
        });
    }


    View.OnClickListener onAddProblem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (houseId == 0) {
                onFailure("Error loading house identifier");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("tenantId", tenantId);
            bundle.putInt("houseId", houseId);
            NavHostFragment.findNavController(TenantProblemFragment.this)
                    .navigate(R.id.action_tenantStatePagerFragment_to_cameraFragment, bundle);
        }
    };


    public TenantProblemFragment setTenantId(int id) {
        this.tenantId = id;
        return this;
    }

}
