package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.ProblemsRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.TenantNameRecyclerViewAdapter;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observers.ProblemsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.ProblemViewModel;
import com.sneydr.roomrv2.databinding.FragmentHomeownerProblemsBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeownerProblemsFragment extends FragmentTemplate implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener, ProblemsObserver {

    private ProblemsRecyclerViewAdapter adapter;
    private FragmentHomeownerProblemsBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homeowner_problems, container, false);
        binding.rcyHomeownerProblems.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.swrHomeownerProblems.setOnRefreshListener(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ProblemViewModel problemViewModel = ViewModelProviders.of(this).get(ProblemViewModel.class);
        problemViewModel.loadProblems(houseId, authToken,this);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter = new ProblemsRecyclerViewAdapter(new ArrayList<>());
        binding.rcyHomeownerProblems.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        Problem problem = adapter.getItemAtPosition(position);
        bundle.putString("authToken", authToken);
        bundle.putInt("houseId", houseId);
        bundle.putInt("problemId", problem.getProblemId());
        NavHostFragment.findNavController(HomeownerProblemsFragment.this).navigate(R.id.action_houseDetailStatePagerFragment_to_viewProblemFragment, bundle);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onProblems(List<Problem> problems) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.swrHomeownerProblems.isRefreshing()){
                    binding.swrHomeownerProblems.setRefreshing(false);
                }
                if (problems.isEmpty()){
                    binding.rcyHomeownerProblems.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                else {
                    binding.rcyHomeownerProblems.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                }
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
                binding.rcyHomeownerProblems.setLayoutAnimation(animation);
                Collections.reverse(problems);
                adapter = new ProblemsRecyclerViewAdapter(problems);
                adapter.setItemClickListener(HomeownerProblemsFragment.this);
                binding.rcyHomeownerProblems.swapAdapter(adapter, false);
            }
        });
    }

    @Override
    public void onFailure(String response) {
        super.onFailure(response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.rcyHomeownerProblems.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new ProblemsRecyclerViewAdapter(new ArrayList<>());
                binding.rcyHomeownerProblems.swapAdapter(adapter, false);
            }
        });
    }



    @Override
    public void onRefresh() {
        ProblemViewModel problemViewModel = ViewModelProviders.of(this).get(ProblemViewModel.class);
        problemViewModel.loadProblems(houseId, authToken,this);
    }
}
