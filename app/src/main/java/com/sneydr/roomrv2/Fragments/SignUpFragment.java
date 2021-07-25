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
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.Network.Observers.SignUpRequestObserver;
import com.sneydr.roomrv2.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.fragment.NavHostFragment;

import okhttp3.Request;

import static com.sneydr.roomrv2.App.Constants.SERVER_URL;

public class SignUpFragment extends FragmentTemplate implements AddHouseURLObserver {

    private WebView webView;
    private String authToken;
    private Network network;

    private void initUI(View view) {
        webView = view.findViewById(R.id.webView);
        network = Network.getInstance();
        webView.setWebViewClient(getWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(false);
        webSettings.setJavaScriptEnabled(true);

        if (network.isNetworkAvailable(getActivity().getApplication())){
            Request request = network.getURL(SERVER_URL, authToken);
            network.send(request, NetworkCallbackType.GetAddHouseURL, this);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);

        initUI(view);

        return view;
    }


    private WebViewClient getWebViewClient() {
        return new WebViewClient() {



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {

                if (network.isNetworkAvailable(getActivity().getApplication())) {
                    Request request = network.getURL(webResourceRequest.getUrl().toString(), authToken);
                    network.send(request, NetworkCallbackType.GetAddHouseURL, SignUpFragment.this);
                }
                return false;
            }


        };
    }





    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @Override
    public void onFailure(String tag, String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.stopLoading();
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                try {
                    Dialog dialog = new Dialog(context);
                    dialog.setMessage(response);
                    dialog.buildErrorDialog().show();
                    NavHostFragment.findNavController(SignUpFragment.this).popBackStack();
                }
                catch (IllegalStateException ex) {

                }

            }
        });

    }


    @Override
    public void onAddHouseRequest(String url) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
            }
        });
    }

    @Override
    public void onFormComplete(String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.stopLoading();
                try {
                    NavHostFragment.findNavController(SignUpFragment.this).navigateUp();
                }
                catch (IllegalStateException ex) {

                    //Toast.makeText(context, "Error navigating to houses page. Press <- to exit.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
