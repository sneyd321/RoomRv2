package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sneydr.roomrv2.Adapters.ViewPager2FragmentStateAdapter;
import com.sneydr.roomrv2.App.UI.DepthPageTransformer;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.databinding.StatePagerHouseDetailsBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class HouseDetailStatePagerFragment extends FragmentTemplate {

    private StatePagerHouseDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        binding = DataBindingUtil.inflate(inflater, R.layout.state_pager_house_details, container, false);
        if (bundle != null && bundle.containsKey("houseId") && bundle.containsKey("authToken") && bundle.containsKey("email")){
            int houseId = bundle.getInt("houseId");
            String authToken = bundle.getString("authToken");
            String email = bundle.getString("email");
            ViewPager2FragmentStateAdapter adapter = setupViewPager(authToken, houseId, email);
            binding.houseDetailViewPager.setAdapter(adapter );

            binding.houseDetailViewPager.setOffscreenPageLimit(adapter.getItemCount());

            new TabLayoutMediator(binding.houseDetailTabLayout, binding.houseDetailViewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    tab.select();
                }
            }).attach();

            binding.houseDetailViewPager.setPageTransformer(new DepthPageTransformer());
            binding.houseDetailViewPager.setCurrentItem(1);


            binding.houseDetailTabLayout.getTabAt(0).setText("Problems");
            binding.houseDetailTabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_build_black_24dp));
            binding.houseDetailTabLayout.getTabAt(1).setText("Tenants");
            binding.houseDetailTabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_account_box_black_24dp));
            binding.houseDetailTabLayout.getTabAt(2).setText("Documents");
            binding.houseDetailTabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_assignment_black_24dp));
            binding.houseDetailTabLayout.getTabAt(3).setText("Messages");
            binding.houseDetailTabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_message_black_24dp));






        }
        else {
            navigation.navigateBack(this);
        }

        return binding.getRoot();
    }







    private ViewPager2FragmentStateAdapter setupViewPager(String authToken, int houseId, String email){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ProblemsFragment().setAuthToken(authToken).setHouseId(houseId));
        fragments.add(new TenantsFragment().setAuthToken(authToken).setHouseId(houseId));
        fragments.add(new DocumentsFragment().setAuthToken(authToken).setHouseId(houseId).setEmail(email));
        fragments.add(new MessageFragment().setAuthToken(authToken).setHouseId(houseId));
        return new ViewPager2FragmentStateAdapter(getChildFragmentManager(), getLifecycle(), fragments);

    }
}
