package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;


import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.DocumentViewModel;


import static com.sneydr.roomrv2.App.Constants.SERVER_URL;

public class LeaseWebFragment extends WebFragmentTemplate {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.sign_up_web, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken") && bundle.containsKey("houseId") && bundle.containsKey("email")){
            authToken = bundle.getString("authToken");
            int houseId = bundle.getInt("houseId");
            email = bundle.getString("email");

            ViewModelProviders
                .of(this)
                .get(DocumentViewModel.class)
                .getDocumentsURL(authToken, SERVER_URL + "RentDetails/Ontario/" + houseId, this);
            webView = view.findViewById(R.id.webView);
            initWebView(webView);
        }
        else {
            NavHostFragment
                .findNavController(this)
                .navigateUp();
        }
        return view;
    }

    @Override
    protected WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest webResourceRequest) {
                ViewModelProviders
                    .of(LeaseWebFragment.this)
                    .get(DocumentViewModel.class)
                    .getDocumentsURL(authToken, webResourceRequest.getUrl().toString(), LeaseWebFragment.this);
                return false;
            }
        };
    }

    @Override
    public void onFormComplete(String message) {
        super.onFormComplete(message);
        scheduleJob(email, "OntarioLease");
    }
}
