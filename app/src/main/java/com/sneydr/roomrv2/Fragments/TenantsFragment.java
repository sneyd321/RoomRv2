package com.sneydr.roomrv2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.UnapprovedState;
import com.sneydr.roomrv2.Adapters.DocumentsRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.StatefulItemClickListener;
import com.sneydr.roomrv2.Adapters.TenantNameRecyclerViewAdapter;
import com.sneydr.roomrv2.App.DatePicker.SelectDateDialog;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.LeaseObserver;
import com.sneydr.roomrv2.Network.Observers.TenantsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.TenantViewModel;
import com.sneydr.roomrv2.databinding.FragmentGenerateLeaseBinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TenantsFragment extends FragmentTemplate implements StatefulItemClickListener, TenantsObserver {

    TenantNameRecyclerViewAdapter adapter;

    private FragmentGenerateLeaseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate_lease, container, false);
        binding.rcyGenerateLeaseTenantNames.setLayoutManager(new LinearLayoutManager(context));
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new TenantNameRecyclerViewAdapter(new ArrayList<>());
        TenantViewModel tenantViewModel = ViewModelProviders.of(this).get(TenantViewModel.class);
        tenantViewModel.getTenantByHouseId(houseId, authToken, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter = new TenantNameRecyclerViewAdapter(new ArrayList<>());
        binding.rcyGenerateLeaseTenantNames.setAdapter(adapter);
    }

    @Override
    public void onFailure(String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter = new TenantNameRecyclerViewAdapter(new ArrayList<>());
                binding.rcyGenerateLeaseTenantNames.setAdapter(adapter);
            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onTenants(List<Tenant> tenants) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter = new TenantNameRecyclerViewAdapter(tenants);
                binding.rcyGenerateLeaseTenantNames.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position, ButtonStateContext buttonStateContext) {
        Tenant tenant = adapter.getTenantAtPosition(position);
        tenant.setApproved(false);
        tenant.setHouseId(houseId);
        if (buttonStateContext.getState() instanceof UnapprovedState) {
            tenant.setApproved(true);
            tenant.setHouseId(houseId);
        }
        //tenantViewModel.updateTenant(tenant, authToken, TenantsFragment.this);
    }







}
