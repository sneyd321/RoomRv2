package com.sneydr.roomrv2.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.util.List;

import okhttp3.Request;

public abstract class FragmentTemplate extends Fragment implements NetworkObserver, LifecycleOwner {


    protected Permission permission;
    protected Network network;
    protected Context context;

    protected abstract void initUI(View view);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        permission = new Permission(getActivity());
        network = Network.getInstance();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void validateForm(List<TextInput> textInputs) {
        for (TextInput textInput : textInputs) {
            textInput.invokeValidation();
            if (textInput.getError() != null) {
                return;
            }
        }
    }

    private void doesHaveInternetPermission() {
        if (!permission.doesHaveInternetPermission()){
            permission.requestInternetPermission();
        }
    }

    private void isInternetAvailable() {
        if (!network.isNetworkAvailable(getActivity().getApplication())){
            Dialog dialog = new Dialog(getActivity());
            dialog.setMessage("Could not connect to the internet.");
            dialog.buildErrorDialog().show();
        }
    }

    @Override
    public void onFailure(String response) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isAdded()){
                    Dialog errorDialog = new Dialog(getActivity());
                    errorDialog.setMessage(response);
                    errorDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    });
                    errorDialog.buildErrorDialog().show();
                }
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

}
