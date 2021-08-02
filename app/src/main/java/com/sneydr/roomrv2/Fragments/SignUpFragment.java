package com.sneydr.roomrv2.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.Network.Observers.SignUpRequestObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import okhttp3.Request;

import static com.sneydr.roomrv2.App.Constants.SERVER_URL;

public class SignUpFragment extends WebFragmentTemplate {


    private HomeownerViewModel homeownerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);
        ViewModelProviders
            .of(this)
            .get(HomeownerViewModel.class)
            .getSignInUrl(authToken, SERVER_URL, this);
        webView = view.findViewById(R.id.webView);
        initWebView(webView);
        return view;
    }


    protected WebViewClient getWebViewClient() {
        return new WebViewClient() {



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {
                ViewModelProviders
                    .of(SignUpFragment.this)
                    .get(HomeownerViewModel.class)
                    .getSignInUrl(authToken, webResourceRequest.getUrl().toString(), SignUpFragment.this);
                return false;
            }


        };
    }

}
