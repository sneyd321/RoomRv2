package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.sneydr.roomrv2.Adapters.ViewPager2FragmentStateAdapter;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import okhttp3.Request;

public class SignUpStatePagerFragment extends FragmentTemplate  {

    private TabLayout tabLayout;

    private HomeownerSignUpFragment homeownerSignUpFragment;
    private TenantSignUpFragment tenantSignUpFragment;

    @Override
    protected void initUI(View view) {
        tabLayout = view.findViewById(R.id.signUpTabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.signUpViewPager);
        Button btnSignUp = view.findViewById(R.id.btnTSUsignup);
        btnSignUp.setOnClickListener(onSignUp);
        Button btnBack = view.findViewById(R.id.btnTSUBack);
        btnBack.setOnClickListener(onBack);
        homeownerSignUpFragment = new HomeownerSignUpFragment();
        tenantSignUpFragment = new TenantSignUpFragment();

        FragmentStateAdapter adapter = setupViewPager();
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("")).attach();
        tabLayout.getTabAt(0).setText("Homeowner");
        tabLayout.getTabAt(1).setText("Tenant");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sign_up_state_pager, container, false);
        initUI(view);
        return view;
    }




    private View.OnClickListener onSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (tabLayout.getSelectedTabPosition()) {
                case 0:
                    NetworkCallback homeownerCallback = network.getCallback(NetworkCallbackType.GetHomeowner);
                    homeownerCallback.registerObserver(SignUpStatePagerFragment.this);
                    Request homeownerRequest = network.postHomeowner(homeownerSignUpFragment.getHomeowner());
                    send(homeownerRequest, homeownerCallback);
                    break;
                case 1:
                    NetworkCallback tenantCallback = network.getCallback(NetworkCallbackType.GetTenant);
                    tenantCallback.registerObserver(SignUpStatePagerFragment.this);
                    Request tenantRequest = network.postTenant(tenantSignUpFragment.getTenant());
                    send(tenantRequest, tenantCallback);
                    break;
            }
        }
    };



    private View.OnClickListener onBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().onBackPressed();
        }
    };


    private FragmentStateAdapter setupViewPager(){
        ViewPager2FragmentStateAdapter adapter = new ViewPager2FragmentStateAdapter(getActivity());
        adapter.addFragment(homeownerSignUpFragment);
        adapter.addFragment(tenantSignUpFragment);
        return adapter;
    }




}
