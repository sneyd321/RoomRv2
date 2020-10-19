package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sneydr.roomrv2.Adapters.ViewPager2FragmentStateAdapter;
import com.sneydr.roomrv2.App.UI.HouseDetail;
import com.sneydr.roomrv2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class HouseDetailStatePagerFragment extends Fragment implements LifecycleOwner {


    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FragmentStateAdapter adapter;

    private List<HouseDetail> houseDetails;
    private List<HouseDetail> rentDetails;
    private List<HouseDetail> agreementDetails;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_detail_state_pager, container, false);
        tabLayout = view.findViewById(R.id.houseDetailTabLayout);
        viewPager = view.findViewById(R.id.houseDetailViewPager);

        houseDetails = new ArrayList<>();
        houseDetails.add(new HouseDetail("Edit Rental Unit Location", R.drawable.ic_my_location_black_24dp));
        houseDetails.add(new HouseDetail("Edit House Display Picture", R.drawable.ic_add_a_photo_black_24dp));

        rentDetails = new ArrayList<>();
        rentDetails.add(new HouseDetail("Edit Rent Details", R.drawable.ic_monetization_on_black_24dp));
        rentDetails.add(new HouseDetail("Edit Utilities", R.drawable.ic_power_black_24dp));
        rentDetails.add(new HouseDetail("Edit Amenities", R.drawable.ic_build_black_24dp));

        agreementDetails = new ArrayList<>();
        agreementDetails.add(new HouseDetail("Add Start and End Date", R.drawable.ic_today_black_24dp));
        agreementDetails.add(new HouseDetail("Add Tenant Names", R.drawable.ic_accessibility_black_24dp));

        adapter = setupViewPager();
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("")).attach();

        tabLayout.getTabAt(0).setText("House");
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        tabLayout.getTabAt(1).setText("Rent");
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_monetization_on_black_24dp));
        tabLayout.getTabAt(2).setText("Lease");
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_assignment_black_24dp));


        return view;
    }


    private FragmentStateAdapter setupViewPager(){
        ViewPager2FragmentStateAdapter adapter = new ViewPager2FragmentStateAdapter(getActivity());
        adapter.addFragment(new HouseDetailFragment().setData(houseDetails));
        adapter.addFragment(new HouseDetailFragment().setData(rentDetails));
        adapter.addFragment(new HouseDetailFragment().setData(agreementDetails));
        return adapter;
    }



}
