package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;


import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;
import com.sneydr.roomrv2.databinding.FragmentHouseBinding;


import java.util.ArrayList;
import java.util.List;

public class HousesFragment extends FragmentTemplate implements ItemClickListener, HousesObserver, LifecycleObserver, View.OnClickListener {


    private HousesRecyclerViewAdapter adapter;
    private FragmentHouseBinding binding;
    private String authToken;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken")){
            authToken = bundle.getString("authToken");
            HouseViewModel houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
            binding.rcyHouses.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new HousesRecyclerViewAdapter(new ArrayList<>());
            binding.rcyHouses.setAdapter(adapter);
            houseViewModel.getHouses(authToken, this);
            binding.btnHousesAddHouse.setOnClickListener(this);
        }
        else {
            NavHostFragment.findNavController(this).popBackStack();
        }
        return view;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onHouses(List<House> houses) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
                binding.rcyHouses.setLayoutAnimation(animation);
                adapter = new HousesRecyclerViewAdapter(houses);
                adapter.setOnClickListener(HousesFragment.this);
                binding.rcyHouses.swapAdapter(adapter, true);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        House house = adapter.getItemAtPosition(position);
        Bundle bundle = new Bundle();
        bundle.putInt("houseId", house.getHouseId());
        bundle.putString("authToken", authToken);
        NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_houseDetailStatePagerFragment, bundle);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        bundle.putString("authToken", authToken);
        NavHostFragment.findNavController(this).navigate(R.id.action_housesFragment_to_addHouseFragment, bundle);
    }
}
