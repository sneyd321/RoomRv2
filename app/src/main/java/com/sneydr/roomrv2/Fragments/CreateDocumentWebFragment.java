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
import androidx.navigation.fragment.NavHostFragment;

import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.R;

import okhttp3.Request;

public class CreateDocumentWebFragment extends FragmentTemplate implements AddHouseURLObserver {

    private WebView webView;
    private String authToken;
    private Network network;
    private int houseId;

    private void initUI(View view) {
        webView = view.findViewById(R.id.webView);
        network = Network.getInstance();
        webView.setWebViewClient(getWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (network.isNetworkAvailable(getActivity().getApplication())){
            Request request = network.getURL(network.getServerUrl() + "RentDetails/Ontario/" + houseId, authToken);
            network.send(request, NetworkCallbackType.GetAddHouseURL, this);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken") && bundle.containsKey("houseId")){
            authToken = bundle.getString("authToken");
            houseId = bundle.getInt("houseId");
            initUI(view);
        }
        else {
            NavHostFragment.findNavController(this).popBackStack();
        }
        return view;
    }


    private WebViewClient getWebViewClient() {
        return new WebViewClient() {



            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {

                if (network.isNetworkAvailable(getActivity().getApplication())) {
                    Request request = network.getURL(webResourceRequest.getUrl().toString(), authToken);
                    network.send(request, NetworkCallbackType.GetAddHouseURL, CreateDocumentWebFragment.this);
                }
                return false;
            }


        };
    }





    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @Override
    public void onFailure(String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.stopLoading();
                Dialog dialog = new Dialog(context);
                dialog.setMessage(response);
                dialog.buildErrorDialog().show();
                NavHostFragment.findNavController(CreateDocumentWebFragment.this).popBackStack();
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
                    NavHostFragment.findNavController(CreateDocumentWebFragment.this).popBackStack();
                }
                catch (IllegalStateException ex) {

                    //Toast.makeText(context, "Error navigating to houses page. Press <- to exit.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
