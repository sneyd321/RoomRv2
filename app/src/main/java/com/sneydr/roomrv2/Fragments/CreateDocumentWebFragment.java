package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.DocumentViewModel;

import okhttp3.Request;

import static com.sneydr.roomrv2.App.Constants.SERVER_URL;

public class CreateDocumentWebFragment extends WebFragmentTemplate {


    private DocumentViewModel documentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken") && bundle.containsKey("houseId")){
            authToken = bundle.getString("authToken");
            int houseId = bundle.getInt("houseId");
            documentViewModel = ViewModelProviders.of(this).get(DocumentViewModel.class);
            documentViewModel.getDocumentsURL(authToken, SERVER_URL + "RentDetails/Ontario/" + houseId, this);
            webView = view.findViewById(R.id.webView);
            initWebView(webView);
        }
        else {
            NavHostFragment.findNavController(this).navigateUp();
        }
        return view;
    }

    @Override
    protected WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {
                documentViewModel.getDocumentsURL(authToken, webResourceRequest.getUrl().toString(), CreateDocumentWebFragment.this);
                return false;
            }
        };
    }

}
