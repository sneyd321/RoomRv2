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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;

import static com.sneydr.roomrv2.App.Constants.SERVER_URL;


public class AddHouseWebFragment extends WebFragmentTemplate {

    private HouseViewModel houseViewModel;


    @Override
    protected WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {
                houseViewModel.getHouseURL(authToken, webResourceRequest.getUrl().toString(), AddHouseWebFragment.this);
                return false;
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken")){
            authToken = bundle.getString("authToken");
            webView = view.findViewById(R.id.webView);
            initWebView(webView);
            houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
            houseViewModel.getHouseURL(authToken, SERVER_URL + "House", this);
        }
        else {
            NavHostFragment.findNavController(this).navigateUp();
        }
        return view;
    }
}
