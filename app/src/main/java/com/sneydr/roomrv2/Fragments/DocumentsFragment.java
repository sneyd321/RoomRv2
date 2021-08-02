package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
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


import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.Adapters.DocumentsRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.App.Constants;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Network.Observers.DocumentsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.DocumentViewModel;
import com.sneydr.roomrv2.databinding.FragmentViewDocumentsBinding;

import java.util.ArrayList;
import java.util.List;

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

        return binding.getRoot();
    }



    @Override
    public void onResume() {
        super.onResume();
        ViewModelProviders.of(this).get(DocumentViewModel.class).getDocuments(houseId, authToken, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.swrDocuments.setRefreshing(false);
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onDocuments(List<Document> documents) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.swrDocuments.setRefreshing(false);
                adapter = new DocumentsRecyclerViewAdapter(documents);
                adapter.setOnCreateButtonClickListener(DocumentsFragment.this);
                adapter.setOnDownloadButtonClickListener(DocumentsFragment.this);
                binding.rcyDocuments.setAdapter(adapter);

            }
        });
    }

    @Override
    public void onFailure(String tag, String response) {
        super.onFailure(tag, response);
        handler.post(new Runnable() {
            @Override
            public void run() {
                binding.swrDocuments.setRefreshing(false);
                adapter = new DocumentsRecyclerViewAdapter(new ArrayList<>());
                binding.rcyDocuments.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onCreateButtonClick(View view, int position) {
        navigation.navigate(this, R.id.action_houseDetailStatePagerFragment_to_createDocumentWebFragment, authToken, houseId, email);
    }


    @Override
    public void onDownloadButtonClick(View view, int position) {
        if (!permission.doesHaveWritePermission()) {
            permission.requestWriteExternalStoragePermission();
            return;
        }
        Document document = adapter.getItemAtPosition(position);
        MainActivityLandlord activity = (MainActivityLandlord) getActivity();
        if (activity != null)
            activity.startActivityForResult(intentFactory.getDocumentIntent(document), Constants.DOCUMENT_INTENT);

    }

    @Override
    public void onRefresh() {
        ViewModelProviders.of(this).get(DocumentViewModel.class).getDocuments(houseId, authToken, this);
    }



}
