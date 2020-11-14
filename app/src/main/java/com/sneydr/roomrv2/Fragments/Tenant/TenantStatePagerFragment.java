package com.sneydr.roomrv2.Fragments.Tenant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sneydr.roomrv2.Adapters.ViewPager2FragmentStateAdapter;
import com.sneydr.roomrv2.Fragments.FragmentTemplate;
import com.sneydr.roomrv2.Fragments.GenerateLeaseFragment;
import com.sneydr.roomrv2.Fragments.HomeownerMessageFragment;
import com.sneydr.roomrv2.Fragments.HomeownerProblemFragment;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.R;

import okhttp3.Request;

public class TenantStatePagerFragment extends FragmentTemplate {



    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FragmentStateAdapter adapter;
    private int houseId;
    private int tenantId;

    @Override
    protected void initUI(View view) {
        tabLayout = view.findViewById(R.id.tenantTabLayout);
        viewPager = view.findViewById(R.id.tenantStatePager);
        adapter = setupViewPager();
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                viewPager.setCurrentItem(1);
            }
        }).attach();

        tabLayout.getTabAt(1).setText("Home");
        tabLayout.getTabAt(1).setIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp));
        tabLayout.getTabAt(0).setText("Problems");
        tabLayout.getTabAt(0).setIcon(getResources().getDrawable(R.drawable.ic_build_black_24dp));
        tabLayout.getTabAt(2).setText("Messages");
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_message_black_24dp));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.state_pager_tenant, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("tenantId")){
            tenantId = bundle.getInt("tenantId");
            initUI(view);
            return view;
        }
        onDestroy();
        return view;
    }

    private FragmentStateAdapter setupViewPager(){
        ViewPager2FragmentStateAdapter adapter = new ViewPager2FragmentStateAdapter(getActivity());
        adapter.addFragment(new TenantProblemFragment().setTenantId(tenantId));
        adapter.addFragment(new TenantLandingFragment().setTenantId(tenantId));
        adapter.addFragment(new TenantMessageFragment().setTenantId(tenantId));
        return adapter;
    }
}
