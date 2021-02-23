package com.sneydr.roomrv2.Fragments;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rabbitmq.client.Channel;
import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.Adapters.ButtonState.ApprovedState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.Adapters.ButtonState.UnapprovedState;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.StatefulItemClickListener;
import com.sneydr.roomrv2.Adapters.ProblemsRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.TenantNameRecyclerViewAdapter;
import com.sneydr.roomrv2.App.DatePicker.SelectDateDialog;
import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.LeaseObserver;
import com.sneydr.roomrv2.Network.Observers.TenantObserver;
import com.sneydr.roomrv2.Network.Observers.TenantsObserver;
import com.sneydr.roomrv2.RabbitMQ.ConsumeFailedCallback;
import com.sneydr.roomrv2.RabbitMQ.ConsumeLeaseCallback;
import com.sneydr.roomrv2.RabbitMQ.RabbitCallback;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQ;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQObserver;
import com.sneydr.roomrv2.Repositories.LeaseRepository;
import com.sneydr.roomrv2.Services.LeaseService;
import com.sneydr.roomrv2.ViewModels.LeaseViewModel;
import com.sneydr.roomrv2.ViewModels.TenantViewModel;
import com.sneydr.roomrv2.databinding.FragmentGenerateLeaseBinding;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class GenerateLeaseFragment extends FragmentTemplate implements StatefulItemClickListener, LeaseObserver, TenantsObserver, View.OnClickListener {

    SelectDateDialog edtStartDate;
    SelectDateDialog edtEndDate;
    TenantNameRecyclerViewAdapter adapter;
    private Intent intent;
    private int houseId;
    private String authToken;
    private TenantViewModel tenantViewModel;
    private LeaseViewModel leaseViewModel;
    private FragmentGenerateLeaseBinding binding;
    private RabbitMQ rabbitMQ = RabbitMQ.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate_lease, container, false);

        leaseViewModel =  ViewModelProviders.of(this).get(LeaseViewModel.class);

        edtStartDate = new SelectDateDialog(binding.getRoot(), R.id.edtGenerateLeaseStartDate);
        edtEndDate = new SelectDateDialog(binding.getRoot(), R.id.edtGenerateLeaseEndDate);

        binding.btnDownloadLease.setOnClickListener(this);
        binding.rcyGenerateLeaseTenantNames.setLayoutManager(new LinearLayoutManager(context));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new TenantNameRecyclerViewAdapter(new ArrayList<>());
        tenantViewModel = ViewModelProviders.of(this).get(TenantViewModel.class);
        tenantViewModel.getTenantByHouseId(houseId, authToken, this);
    }

    public GenerateLeaseFragment setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public GenerateLeaseFragment setHomeownerId(String authToken) {
        this.authToken = authToken;
        return this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onTenants(List<Tenant> tenants) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter = new TenantNameRecyclerViewAdapter(tenants);
                adapter.setItemClickListener(GenerateLeaseFragment.this);
                binding.rcyGenerateLeaseTenantNames.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position, ButtonStateContext buttonStateContext) {
        Tenant tenant = adapter.getTenantAtPosition(position);
        tenant.setApproved(false);
        if (buttonStateContext.getState() instanceof UnapprovedState) {
            tenant.setApproved(true);
        }
        tenantViewModel.updateTenant(tenant, authToken,GenerateLeaseFragment.this);
    }

    @Override
    public void onClick(View v) {
        Lease lease = new Lease(adapter.getData(), edtStartDate.getText(), edtEndDate.getText(), houseId, authToken);
        leaseViewModel.sendLease(lease, authToken, this);
        intent = new Intent(getActivity(), LeaseService.class);
        intent.putExtra("queueName", lease.getQueueName());

    }

    @Override
    public void onLease() {
        rabbitMQ.registerObserver((MainActivityLandlord)getActivity());
        ContextCompat.startForegroundService(context, intent);
    }


}
