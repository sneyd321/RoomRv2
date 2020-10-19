package com.sneydr.roomrv2.Fragments;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rabbitmq.client.Channel;
import com.sneydr.roomrv2.Adapters.ItemClickListener;
import com.sneydr.roomrv2.Adapters.TenantNameRecyclerViewAdapter;
import com.sneydr.roomrv2.App.DatePicker.SelectDateDialog;
import com.sneydr.roomrv2.Database.LeaseRepository;
import com.sneydr.roomrv2.Database.Tenant.TenantViewModel;
import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.RabbitMQ.ConsumeFailedCallback;
import com.sneydr.roomrv2.RabbitMQ.ConsumeLeaseCallback;
import com.sneydr.roomrv2.RabbitMQ.RabbitCallback;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQ;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.RabbitMQ.RabbitMQObserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.Request;

public class GenerateLeaseFragment extends FragmentTemplate implements RabbitMQObserver, ItemClickListener {

    SelectDateDialog edtStartDate;
    SelectDateDialog edtEndDate;
    RecyclerView rcyTenantName;
    TenantNameRecyclerViewAdapter adapter;
    private CardView crdNoTenants;
    Intent intent;
    TenantViewModel tenantViewModel;
    private int houseId;
    private LeaseRepository leaseRepository;
    private byte[] pdfBytes;


    @Override
    protected void initUI(View view) {
        edtStartDate = new SelectDateDialog(view, R.id.edtGenerateLeaseStartDate);
        edtEndDate = new SelectDateDialog(view, R.id.edtGenerateLeaseEndDate);
        crdNoTenants = view.findViewById(R.id.crdNoTenants);
        Button btnDownload = view.findViewById(R.id.btnLeaseDownload);
        btnDownload.setOnClickListener(onDownload);
        Button btnBack = view.findViewById(R.id.btnLeaseBack);
        rcyTenantName = view.findViewById(R.id.rcyGenerateLeaseTenantNames);
        rcyTenantName.setLayoutManager(new LinearLayoutManager(getActivity()));
        intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        tenantViewModel = ViewModelProviders.of(this).get(TenantViewModel.class);
        leaseRepository = new LeaseRepository((Application) context.getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_generate_lease, container, false);
        initUI(view);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("houseId")){
            houseId = bundle.getInt("houseId");
            tenantViewModel.getTenantsByHouseId(houseId).observe(getViewLifecycleOwner(), observeTenants);
            NetworkCallback callback = network.getCallback(NetworkCallbackType.GetTenants);
            callback.registerObserver(this);
            Request request = network.getTenants(houseId);
            send(request, callback);
        }
        return view;
    }

    @Override
    public void onLease(byte[] lease) {
        pdfBytes = lease;
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_TITLE, "Ontario Lease Agreement");
        intent.setType("application/pdf");
        startActivityForResult(intent, 3);
    }

    private Observer<List<Tenant>> observeTenants = new Observer<List<Tenant>>() {
        @Override
        public void onChanged(List<Tenant> tenants) {
            if (tenants.size() == 0) {
                crdNoTenants.setVisibility(View.VISIBLE);
                return;
            }
            crdNoTenants.setVisibility(View.INVISIBLE);
            adapter = new TenantNameRecyclerViewAdapter(context, tenants);
            adapter.setItemClickListener(GenerateLeaseFragment.this);
            rcyTenantName.swapAdapter(adapter, true);
        }
    };

    @Override
    public void onItemClick(View view, int position) {
        CompoundButton buttonView = (CompoundButton) view;
        Tenant tenant = adapter.getTenantAtPosition(position);
        tenant.setApproved(false);
        if (buttonView.isChecked()) {
            tenant.setApproved(true);
        }
        NetworkCallback callback = network.getCallback(NetworkCallbackType.GetTenant);
        callback.registerObserver(this);
        Request request = network.putTenant(tenant);
        send(request, callback);

    }





    private View.OnClickListener onDownload = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Lease lease = leaseRepository.getLeaseData(houseId);
            if (lease == null) return;
            lease.setStartDate(edtStartDate.getText());
            lease.setEndDate(edtEndDate.getText());

            NetworkCallback networkCallback = network.getCallback(NetworkCallbackType.EmptyCallback);
            networkCallback.registerObserver(GenerateLeaseFragment.this);
            Request request = network.postLease(lease);
            send(request, networkCallback);

            RabbitTask task = new RabbitTask(lease.getQueueName());
            task.execute();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            if (resultData.getData() != null && isAdded()) {
                try {
                    OutputStream outputStream = getActivity().getContentResolver().openOutputStream(resultData.getData());
                    buildPDF(pdfBytes, outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void buildPDF(byte[] bytes, OutputStream output) throws IOException {
        InputStream input = new ByteArrayInputStream(bytes);
        byte[] data = new byte[1024];
        int count;
        while ((count = input.read(data)) > -1) {
            output.write(data, 0, count);
        }
        output.flush();
        output.close();
        input.close();
    }




    private class RabbitTask extends AsyncTask<Void, Void, Void> {

        private String queueName;

        public RabbitTask(String queueName) {
            this.queueName = queueName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            RabbitMQ rabbitMQ = RabbitMQ.getInstance();
            try {
                Channel channel = rabbitMQ.initQueueAndExchange(queueName);
                RabbitCallback successCallback = new ConsumeLeaseCallback();
                successCallback.registerObserver(GenerateLeaseFragment.this);
                channel.basicConsume(queueName, true, successCallback, new ConsumeFailedCallback());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
