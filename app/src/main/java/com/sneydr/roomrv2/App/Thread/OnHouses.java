package com.sneydr.roomrv2.App.Thread;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.fragment.app.Fragment;

import com.sneydr.roomrv2.Adapters.HousesRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.App.Naviagation.Navigation;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Fragments.HousesFragment;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.FragmentHouseBinding;

import java.util.List;

public class OnHouses implements Runnable {

    private List<House> houses;
    private FragmentHouseBinding binding;
    private ItemClickListener itemClickListener;

    public OnHouses(FragmentHouseBinding houseBinding, List<House> houses, ItemClickListener itemClickListener) {
        this.houses = houses;
        this.binding = houseBinding;
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void run() {
        HousesRecyclerViewAdapter adapter = new HousesRecyclerViewAdapter(houses);
        adapter.setOnClickListener(itemClickListener);
        binding.rcyHouses.setAdapter(adapter);
    }


}
