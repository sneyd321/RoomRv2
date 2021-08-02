package com.sneydr.roomrv2.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.navigation.fragment.NavHostFragment;

import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.Network.Observers.AddHouseURLObserver;
import com.sneydr.roomrv2.R;

public abstract class WebFragmentTemplate extends FragmentTemplate implements AddHouseURLObserver {

    protected WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    protected void initWebView(WebView webView) {
        webView.setWebViewClient(getWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected abstract WebViewClient getWebViewClient();


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    @Override
    public void onFailure(String tag, String response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.stopLoading();
                Dialog dialog = new Dialog(context);
                dialog.setMessage(response);
                dialog.buildErrorDialog().show();
                navigation.navigateBack(WebFragmentTemplate.this);
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
                    NavHostFragment.findNavController(WebFragmentTemplate.this).navigateUp();
                }
                catch (IllegalStateException ex) {
                    ex.getMessage();
                }
            }
        });

    }

}
