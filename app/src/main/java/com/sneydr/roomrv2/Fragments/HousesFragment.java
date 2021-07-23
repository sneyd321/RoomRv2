package com.sneydr.roomrv2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;


import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.App.CircleTransform;
import com.sneydr.roomrv2.App.Constants;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Observers.ActivityObserver;
import com.sneydr.roomrv2.Network.Observers.EmptyObserver;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Services.NotificationJobService;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;
import com.sneydr.roomrv2.databinding.FragmentHouseBinding;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HousesFragment extends FragmentTemplate implements ItemClickListener, ActivityObserver, HousesObserver, HomeownerObserver, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    private HousesRecyclerViewAdapter adapter;
    private FragmentHouseBinding binding;
    private String authToken;
    private HomeownerViewModel homeownerViewModel;
    private HouseViewModel houseViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house, container, false);
        View view = binding.getRoot();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken")){
            authToken = bundle.getString("authToken");
            houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
            homeownerViewModel = ViewModelProviders.of(this).get(HomeownerViewModel.class);
            binding.rcyHouses.setLayoutManager(new LinearLayoutManager(context));
            adapter = new HousesRecyclerViewAdapter(new ArrayList<>());
            binding.rcyHouses.setAdapter(adapter);
            houseViewModel.getHouses(authToken, this);
            homeownerViewModel.loadHomeowner(authToken, this);
            binding.btnHousesAddHouse.setOnClickListener(this);
            binding.btnHousesAddHouse.setVisibility(View.GONE);
            binding.swrHouses.setOnRefreshListener(this);
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
                if (houses != null) {
                    LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
                    binding.rcyHouses.setLayoutAnimation(animation);
                    adapter = new HousesRecyclerViewAdapter(houses);
                    adapter.setOnClickListener(HousesFragment.this);
                    binding.rcyHouses.swapAdapter(adapter, true);
                }
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
        try {
            NavHostFragment.findNavController(this).navigate(R.id.action_housesFragment_to_addHouseWebFragment, bundle);
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(context, "An error occurred. Please try again later.", Toast.LENGTH_LONG).show();
        }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onHomeowner(Homeowner homeowner) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (homeowner != null){
                    if (homeowner.getImageURL() == null) {
                        binding.componentHomeownerProfile.imageView2.setImageResource(R.drawable.ic_baseline_account_circle_24);
                    }
                    else {
                        Picasso.get()
                                .load(homeowner.getImageURL())
                                .transform(new CircleTransform(context))
                                .fit()
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                                .error(R.drawable.ic_baseline_account_circle_24)
                                .centerCrop()
                                .into(binding.componentHomeownerProfile.imageView2);
                    }

                    binding.componentHomeownerProfile.textView3.setText(homeowner.getFullName());
                    binding.componentHomeownerProfile.btnLoginSignup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MainActivityLandlord activity = (MainActivityLandlord) getActivity();
                            if (activity != null) {
                                activity.registerObserver(HousesFragment.this);
                                Intent intent = intentFactory.getGalleryIntent();
                                activity.startActivityForResult(intent, Constants.IMAGE_INTENT);
                            }
                            else {
                                Toast.makeText(context, "An Unexpected Error Occurred. Activity Was Null.", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                    binding.componentHomeownerProfile.btnLoginLogin.setOnClickListener(HousesFragment.this);
                }

            }
        });
    }

    @Override
    public void onFile(File file) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                homeownerViewModel.uploadHomeownerProfile(authToken, file, HousesFragment.this);
            }
        });
    }

    @Override
    public void onFailure(String tag, String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.swrHouses.setRefreshing(false);
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
        }
        });
    }



    @Override
    public void onRefresh() {
        homeownerViewModel.loadHomeowner(authToken, this);
        houseViewModel.getHouses(authToken, this);
        binding.swrHouses.setRefreshing(false);
    }
}
