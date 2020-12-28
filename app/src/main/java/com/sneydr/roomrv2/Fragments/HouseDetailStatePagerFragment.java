package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sneydr.roomrv2.Adapters.ViewPager2FragmentStateAdapter;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.StatePagerHouseDetailsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class HouseDetailStatePagerFragment extends Fragment {

    private StatePagerHouseDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        binding = DataBindingUtil.inflate(inflater, R.layout.state_pager_house_details, container, false);
        if (bundle != null && bundle.containsKey("houseId") && bundle.containsKey("authToken")){
            int houseId = bundle.getInt("houseId");
            String authToken = bundle.getString("authToken");

            FragmentStateAdapter adapter = setupViewPager(authToken, houseId);
            binding.houseDetailViewPager.setAdapter(adapter);

            new TabLayoutMediator(binding.houseDetailTabLayout, binding.houseDetailViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    binding.houseDetailViewPager.setCurrentItem(1);
                }
            }).attach();

            binding.houseDetailTabLayout.getTabAt(1).setText("Tenants");
            binding.houseDetailTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_assignment_black_24dp));
            binding.houseDetailTabLayout.getTabAt(0).setText("Problems");
            binding.houseDetailTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_build_black_24dp));
            binding.houseDetailTabLayout.getTabAt(2).setText("Messages");
            binding.houseDetailTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_message_black_24dp));

        }
        else {
            NavHostFragment.findNavController(this).popBackStack();
        }
        return binding.getRoot();
    }


    private FragmentStateAdapter setupViewPager(String authToken, int houseId){
        ViewPager2FragmentStateAdapter adapter = new ViewPager2FragmentStateAdapter(this);
        adapter.addFragment(new HomeownerProblemsFragment().setHouseId(houseId).setHomeownerId(authToken));
        adapter.addFragment(new GenerateLeaseFragment().setHouseId(houseId).setHomeownerId(authToken));
        adapter.addFragment(new HomeownerMessageFragment().setHouseId(houseId).setHomeownerId(authToken));
        return adapter;
    }
}
