package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sneydr.roomrv2.Adapters.TenantProblemRecyclerViewAdapter;
import com.sneydr.roomrv2.R;

import java.util.ArrayList;

public class TenantProblemFragment extends FragmentTemplate {



    private RecyclerView rcyProblems;
    private TenantProblemRecyclerViewAdapter adapter;
    private FloatingActionButton btnAddProblem;


    @Override
    protected void initUI(View view) {
        rcyProblems = view.findViewById(R.id.rcyTenantProblems);
        rcyProblems.setLayoutManager(new LinearLayoutManager(context));
        adapter = new TenantProblemRecyclerViewAdapter(context, new ArrayList<>());
        rcyProblems.setAdapter(adapter);
        btnAddProblem = view.findViewById(R.id.fabAddProblem);
        btnAddProblem.setOnClickListener(onAddProblem);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tenant_problem, container, false);
        initUI(view);
        return view;
    }


    View.OnClickListener onAddProblem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavHostFragment.findNavController(TenantProblemFragment.this).navigate(R.id.action_tenantProblemFragment_to_cameraFragment);
        }
    };

}
