package com.sneydr.roomrv2.Fragments;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sneydr.roomrv2.Adapters.ButtonClickListener;
import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.ItemClickListener;
import com.sneydr.roomrv2.Adapters.LongClickItemListener;
import com.sneydr.roomrv2.Database.Homeowner.HomeownerViewModel;
import com.sneydr.roomrv2.Database.House.HouseRepository;
import com.sneydr.roomrv2.Database.House.HouseViewModel;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.R;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class HousesFragment extends FragmentTemplate implements ItemClickListener, LongClickItemListener, ButtonClickListener {


    private RecyclerView rcyHouses;
    private TextView txtLandlordGreeting;
    private CardView crdNoHouses;
    private HousesRecyclerViewAdapter adapter;
    private HouseViewModel houseViewModel;




    protected void initUI(View view) {
        txtLandlordGreeting = view.findViewById(R.id.txtHousesGreetingName);
        FloatingActionButton btnAddHouse = view.findViewById(R.id.btnHousesAddHouse);
        btnAddHouse.setOnClickListener(onAddHouse);
        rcyHouses = view.findViewById(R.id.rcyHouses);
        crdNoHouses = view.findViewById(R.id.crdNoHouses);
        rcyHouses.setLayoutManager(new LinearLayoutManager(context));
        HomeownerViewModel homeownerViewModel = ViewModelProviders.of(this).get(HomeownerViewModel.class);
        homeownerViewModel.getHomeowner().observe(getViewLifecycleOwner(), homeownerObserver);
        houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
        houseViewModel.getHouses().observe(getViewLifecycleOwner(), housesObserver);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_house, container, false);
        initUI(view);

        return view;
    }



    private View.OnClickListener onAddHouse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_addHouseFragment);
        }
    };


    @Override
    public void onItemClick(View view, int position) {
        NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_houseDetailStatePagerFragment);
    }

    private Observer<Homeowner> homeownerObserver = new Observer<Homeowner>() {
        @Override
        public void onChanged(Homeowner homeowner) {
            if (homeowner != null){
                txtLandlordGreeting.setText(homeowner.getFullName());
                NetworkCallback callback = network.getCallback(NetworkCallbackType.GetHouses);
                callback.registerObserver(HousesFragment.this);
                Request request = network.getHouses(homeowner);
                send(request, callback);
            }


        }
    };


    private Observer<List<House>> housesObserver = new Observer<List<House>>() {
        @Override
        public void onChanged(List<House> houses) {
            if (houses.size() == 0) {
                adapter = new HousesRecyclerViewAdapter(context, houses);
                crdNoHouses.setVisibility(View.VISIBLE);
                rcyHouses.swapAdapter(adapter, true);
                return;
            }
            adapter = new HousesRecyclerViewAdapter(context, houses);
            crdNoHouses.setVisibility(View.INVISIBLE);
            adapter.setItemClickListener(HousesFragment.this);
            adapter.setLongClickListener(HousesFragment.this);
            adapter.setBtnOnLease(HousesFragment.this);
            adapter.setBtnOnEdit(HousesFragment.this);
            rcyHouses.swapAdapter(adapter, true);
        }
    };


    @Override
    public boolean onLongClick(View view, final int position) {
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme).create();
        alertDialog.setTitle("Remove House");
        alertDialog.setMessage("Are you sure you want to remove this property?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Don't Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
        return true;
    }


    @Override
    public void onButtonClick(View view, int position) {
        Bundle bundle = new Bundle();
        House house = adapter.getItemAtPosition(position);
        bundle.putInt("houseId", house.getHouseId());
        switch (view.getId()) {
            case R.id.btnViewLease:
                NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_generateLeaseFragment, bundle);
                break;
            case R.id.btnUpdateHouse:
                NavHostFragment.findNavController(HousesFragment.this).navigate(R.id.action_housesFragment_to_houseDetailStatePagerFragment);
                Toast.makeText(getActivity(), "Edit House", Toast.LENGTH_SHORT).show();
                break;
        }

    }


}
