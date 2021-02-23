package com.sneydr.roomrv2.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.SignUpRequestObserver;
import com.sneydr.roomrv2.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.fragment.NavHostFragment;

import okhttp3.Request;

public class SignUpFragment extends FragmentTemplate implements SignUpRequestObserver {

    private WebView webView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);
        webView = view.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Network network = Network.getInstance();
        if (network.isNetworkAvailable(getActivity().getApplication())){
            Request request = network.getSignInURL();
            network.send(request, NetworkCallbackType.GetSignUpURL, this);
        }

        return view;
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onSignUpRequest(String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @Override
    public void onFailure(String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.stopLoading();
                Dialog dialog = new Dialog(context);
                dialog.setMessage("Error: Sign up currently down for maintenance.");
                dialog.buildErrorDialog().show();
                NavHostFragment.findNavController(SignUpFragment.this).popBackStack();
            }
        });

    }
}
