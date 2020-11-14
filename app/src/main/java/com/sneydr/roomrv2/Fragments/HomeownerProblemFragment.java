package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.ProblemsRecyclerViewAdapter;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.ProblemRepository;

import java.util.List;

import okhttp3.Request;

public class HomeownerProblemFragment extends FragmentTemplate implements ItemClickListener, ProblemObserver {

    private ProblemsRecyclerViewAdapter adapter;
    private RecyclerView rcyProblems;
    private int houseId;
    private ProblemRepository problemRepository;

    @Override
    protected void initUI(View view) {
        problemRepository = new ProblemRepository(this);
        problemRepository.getProblemByHouseId(houseId);
        rcyProblems = view.findViewById(R.id.rcyHomeownerProblems);
    }


    public HomeownerProblemFragment setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_homeowner_problems, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        Problem problem = adapter.getItemAtPosition(position);
        bundle.putInt("problemId", problem.getProblemId());
        NavHostFragment.findNavController(HomeownerProblemFragment.this).navigate(R.id.action_houseDetailStatePagerFragment_to_viewProblemFragment, bundle);
        Toast.makeText(getActivity(), "Edit House", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProblems(List<Problem> problems) {
        if (problems.isEmpty()){
            rcyProblems.setLayoutManager(new LinearLayoutManager(context));
            adapter = new ProblemsRecyclerViewAdapter(context, problems);
            rcyProblems.swapAdapter(adapter, false);
            return;
        }
        rcyProblems.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new ProblemsRecyclerViewAdapter(context, problems);
        adapter.setItemClickListener(HomeownerProblemFragment.this);
        rcyProblems.swapAdapter(adapter, false);
    }
}
