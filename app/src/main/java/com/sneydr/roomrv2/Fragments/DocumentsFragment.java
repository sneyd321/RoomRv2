package com.sneydr.roomrv2.Fragments;

import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sneydr.roomrv2.Adapters.DocumentsRecyclerViewAdapter;
import com.sneydr.roomrv2.Adapters.Listeners.ItemClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnCreateButtonClickListener;
import com.sneydr.roomrv2.Adapters.Listeners.OnDownloadButtonClickListener;
import com.sneydr.roomrv2.Adapters.ProblemsRecyclerViewAdapter;
import com.sneydr.roomrv2.App.ConnectionManager;
import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Network.Observers.DocumentsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.DocumentViewModel;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;
import com.sneydr.roomrv2.ViewModels.ProblemViewModel;
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
    public void onFailure(String response) {
        super.onFailure(response);
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
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onRefresh() {
        DocumentViewModel documentViewModel = ViewModelProviders.of(this).get(DocumentViewModel.class);
        documentViewModel.getDocuments(houseId, authToken, this);
    }


}
