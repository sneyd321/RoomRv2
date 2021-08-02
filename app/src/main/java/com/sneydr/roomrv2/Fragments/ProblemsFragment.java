package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

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
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observers.ProblemsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.ProblemViewModel;
import com.sneydr.roomrv2.databinding.FragmentHomeownerProblemsBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProblemsFragment extends FragmentTemplate implements ItemClickListener, SwipeRefreshLayout.OnRefreshListener, ProblemsObserver {

    private ProblemsRecyclerViewAdapter adapter;
    private FragmentHomeownerProblemsBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homeowner_problems, container, false);
        binding.rcyHomeownerProblems.setLayoutManager(new LinearLayoutManager(context));
        binding.swrHomeownerProblems.setOnRefreshListener(this);
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.rcyHomeownerProblems.setLayoutAnimation(animation);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewModelProviders.of(this).get(ProblemViewModel.class).loadProblems(houseId, authToken,this);
    }
    @Override
    public void onPause() {
        super.onPause();
        binding.swrHomeownerProblems.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Problem problem = adapter.getItemAtPosition(position);
        navigation.navigate(this, R.id.action_houseDetailStatePagerFragment_to_viewProblemFragment, authToken, houseId, problem.getProblemId());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onProblems(List<Problem> problems) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.swrHomeownerProblems.setRefreshing(false);
                RecyclerView.LayoutManager layoutManager = problems.isEmpty() ? new LinearLayoutManager(context) : new GridLayoutManager(context, 2);
                binding.rcyHomeownerProblems.setLayoutManager(layoutManager);
                Collections.reverse(problems);
                adapter = new ProblemsRecyclerViewAdapter(problems);
                adapter.setItemClickListener(ProblemsFragment.this);
                binding.rcyHomeownerProblems.swapAdapter(adapter, false);
            }
        });
    }

    @Override
    public void onFailure(String tag, String response) {
        super.onFailure(tag, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.swrHomeownerProblems.setRefreshing(false);
                adapter = new ProblemsRecyclerViewAdapter(new ArrayList<>());
                binding.rcyHomeownerProblems.setAdapter(adapter);
            }
        });
    }



    @Override
    public void onRefresh() {
        ViewModelProviders.of(this).get(ProblemViewModel.class).loadProblems(houseId, authToken,this);
    }
}
