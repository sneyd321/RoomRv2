package com.sneydr.roomrv2.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Adapter;
import android.widget.Toast;


import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.Adapters.AdapterFactory;
import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.RecyclerViewAdapter;
import com.sneydr.roomrv2.App.Button.AddHouseButton;
import com.sneydr.roomrv2.App.Button.AddPhotoButton;
import com.sneydr.roomrv2.App.CircleTransform;
import com.sneydr.roomrv2.App.Constants;
import com.sneydr.roomrv2.App.Thread.OnFile;
import com.sneydr.roomrv2.App.Thread.OnHomeowner;
import com.sneydr.roomrv2.App.Thread.OnHouses;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Observers.ActivityObserver;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;
import com.sneydr.roomrv2.databinding.FragmentHouseBinding;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class HousesFragment extends FragmentTemplate implements ItemClickListener, ActivityObserver, HousesObserver, HomeownerObserver, SwipeRefreshLayout.OnRefreshListener {


    private FragmentHouseBinding binding;

    private void initBinding(FragmentHouseBinding binding) {
        binding.rcyHouses.setLayoutManager(new LinearLayoutManager(context));
        binding.swrHouses.setOnRefreshListener(this);


        binding.componentHomeownerProfile.btnLoginLogin.setOnClickListener(new AddHouseButton(this, authToken));

        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        binding.rcyHouses.setLayoutAnimation(animation);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken") && bundle.containsKey("email")){
            authToken = bundle.getString("authToken");
            email = bundle.getString("email");

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_house, container, false);
            initBinding(binding);

            ViewModelProviders.of(this).get(HouseViewModel.class).getHouses(authToken, this);
            ViewModelProviders.of(this).get(HomeownerViewModel.class).loadHomeowner(authToken, this);
        }
        else {
            navigation.navigateBack(this);
        }
        return binding.getRoot();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onHouses(List<House> houses) {
        handler.post(new OnHouses(this.binding, houses, this));
    }

    @Override
    public void onItemClick(View view, int position) {
        HousesRecyclerViewAdapter adapter = (HousesRecyclerViewAdapter) binding.rcyHouses.getAdapter();
        if (adapter == null) return;
        House house = adapter.getItemAtPosition(position);
        navigation.navigate(this, R.id.action_housesFragment_to_houseDetailStatePagerFragment, authToken, house.getHouseId(), email);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onHomeowner(Homeowner homeowner) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (homeowner.getImageURL() == null || homeowner.getImageURL().isEmpty()) {
                    binding.componentHomeownerProfile.imageView2.setImageResource(R.drawable.ic_baseline_account_circle_24);
                }
                else {
                    Picasso.get().load(homeowner.getImageURL())
                            .transform(new CircleTransform(binding.getRoot().getContext())).fit()
                            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                            .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                            .error(R.drawable.ic_baseline_account_circle_24)
                            .centerCrop()
                            .into(binding.componentHomeownerProfile.imageView2);
                }
                binding.componentHomeownerProfile.btnLoginSignup.setOnClickListener(new AddPhotoButton(HousesFragment.this, getActivity(), homeowner));
                binding.componentHomeownerProfile.textView3.setText(homeowner.getFullName());
            }
        });
    }

    @Override
    public void onFile(File file) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ViewModelProviders.of(HousesFragment.this).get(HomeownerViewModel.class).uploadHomeownerProfile(authToken, file, HousesFragment.this);
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
        ViewModelProviders.of(this).get(HomeownerViewModel.class).loadHomeowner(authToken, this);
        ViewModelProviders.of(this).get(HouseViewModel.class).getHouses(authToken, this);

        binding.swrHouses.setRefreshing(false);
    }
}
