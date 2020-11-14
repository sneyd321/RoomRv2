package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sneydr.roomrv2.Adapters.Listeners.ButtonClickListener;
import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.HousesObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.HomeownerRepository;
import com.sneydr.roomrv2.Repositories.HouseRepository;


import java.util.List;

import okhttp3.Request;

public class HousesFragment extends FragmentTemplate implements ButtonClickListener, HousesObserver, HomeownerObserver {


    private RecyclerView rcyHouses;
    private HousesRecyclerViewAdapter adapter;
    private int homeownerId;
    private HouseRepository houseRepository;
    private HomeownerRepository homeownerRepository;

    protected void initUI(View view) {
        FloatingActionButton btnAddHouse = view.findViewById(R.id.btnHousesAddHouse);
        btnAddHouse.setOnClickListener(onAddHouse);
        rcyHouses = view.findViewById(R.id.rcyHouses);
        rcyHouses.setLayoutManager(new LinearLayoutManager(context));
        houseRepository = new HouseRepository(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_house, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("homeownerId")){
            homeownerId = bundle.getInt("homeownerId");
            homeownerRepository = new HomeownerRepository(this);
            homeownerRepository.getHomeowner(homeownerId);
            initUI(view);
        }
        return view;
    }

    private View.OnClickListener onAddHouse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("homeownerId", homeownerId);
            NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_addHouseFragment, bundle);
        }
    };

    @Override
    public void onHomeowner(Homeowner homeowner) {
        if (homeowner != null) {
            houseRepository.getHouses(homeowner);
        }
    }

    @Override
    public void onHouses(List<House> houses) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter = new HousesRecyclerViewAdapter(context, houses);
                adapter.setBtnOnEdit(HousesFragment.this);
                rcyHouses.swapAdapter(adapter, true);
            }
        });
    }

    @Override
    public void onButtonClick(View view, int position) {
        Bundle bundle = new Bundle();
        House house = adapter.getItemAtPosition(position);
        bundle.putInt("houseId", house.getHouseId());
        bundle.putInt("homeownerId", house.getHomeownerId());
        NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_houseDetailStatePagerFragment, bundle);
    }

}
