package com.sneydr.roomrv2.Fragments;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sneydr.roomrv2.Adapters.DocumentsRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.App.ConnectionManager;
import com.sneydr.roomrv2.App.Constants;
import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Network.Observers.DocumentsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Services.ProfilePictureNotificationService;
import com.sneydr.roomrv2.ViewModels.DocumentViewModel;
import com.sneydr.roomrv2.databinding.FragmentViewDocumentsBinding;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class DocumentsFragment extends FragmentTemplate implements SwipeRefreshLayout.OnRefreshListener, DocumentsObserver, OnCreateButtonClickListener, OnDownloadButtonClickListener {


    private FragmentViewDocumentsBinding binding;
    private DocumentsRecyclerViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_documents, container, false);
        binding.rcyDocuments.setLayoutManager(new LinearLayoutManager(context));
        binding.swrDocuments.setOnRefreshListener(this);
        scheduleJob();
        return binding.getRoot();
    }

    public void scheduleJob() {
        Activity activity = getActivity();
        if (activity == null) return;

        ComponentName componentName = new ComponentName(getActivity(), ProfilePictureNotificationService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        DocumentViewModel documentViewModel = ViewModelProviders.of(this).get(DocumentViewModel.class);
        documentViewModel.getDocuments(houseId, authToken, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        DocumentsRecyclerViewAdapter adapter = new DocumentsRecyclerViewAdapter(new ArrayList<>());
        binding.rcyDocuments.setAdapter(adapter);
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onDocuments(List<Document> documents) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.swrDocuments.isRefreshing()){
                    binding.swrDocuments.setRefreshing(false);
                }
                adapter = new DocumentsRecyclerViewAdapter(documents);
                adapter.setOnCreateButtonClickListener(DocumentsFragment.this);
                adapter.setOnDownloadButtonClickListener(DocumentsFragment.this);
                binding.rcyDocuments.swapAdapter(adapter, true);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailure(String tag, String response) {
        super.onFailure(tag, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (binding.swrDocuments.isRefreshing()){
                    binding.swrDocuments.setRefreshing(false);
                }
                binding.rcyDocuments.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new DocumentsRecyclerViewAdapter(new ArrayList<>());
                binding.rcyDocuments.swapAdapter(adapter, false);
            }
        });
    }

    @Override
    public void onCreateButtonClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("authToken", authToken);
        bundle.putInt("houseId", houseId);
        try {
            NavHostFragment.findNavController(this).navigate(R.id.action_houseDetailStatePagerFragment_to_createDocumentWebFragment, bundle);
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(context, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDownloadButtonClick(View view, int position) {
        Permission permission = new Permission(context);
        if (!permission.doesHaveWritePermission()) {
            permission.requestWriteExternalStoragePermission();
        }
        else {
            Document document = adapter.getItemAtPosition(position);
            ConnectionManager connectionManager = ConnectionManager.getInstance();
            connectionManager.setLeaseUrl(document.getDocumentURL());
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/pdf");
            intent.putExtra(Intent.EXTRA_TITLE, document.getName());
            getActivity().startActivityForResult(intent, Constants.DOCUMENT_INTENT);
        }
    }

    @Override
    public void onRefresh() {
        DocumentViewModel documentViewModel = ViewModelProviders.of(this).get(DocumentViewModel.class);
        documentViewModel.getDocuments(houseId, authToken, this);
    }



}
