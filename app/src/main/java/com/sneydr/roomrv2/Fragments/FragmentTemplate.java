package com.sneydr.roomrv2.Fragments;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.App.App;
import com.sneydr.roomrv2.App.Dialog.Dialog;
import com.sneydr.roomrv2.App.Permission;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Database.Homeowner.HomeownerRepository;
import com.sneydr.roomrv2.Database.House.HouseRepository;
import com.sneydr.roomrv2.Database.RoomRDatabase;
import com.sneydr.roomrv2.Database.Tenant.TenantRepository;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.NetworkObserver;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

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





    @Override
    public void onHomeowner(Homeowner homeowner) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                HomeownerRepository homeownerRepository = new HomeownerRepository((Application) context.getApplicationContext());
                homeownerRepository.insert(homeowner);
                Intent intent = new Intent(getActivity(), MainActivityLandlord.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onHouses(List<House> houses) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                HouseRepository houseRepository = new HouseRepository((Application) context.getApplicationContext());
                for (House house : houses) {
                    houseRepository.insert(house);
                }
            }
        });

    }

    @Override
    public void onTenants(List<Tenant> tenants) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                TenantRepository tenantRepository = new TenantRepository((Application) context.getApplicationContext());
                for (Tenant tenant : tenants) {
                    tenantRepository.insert(tenant);
                }
            }
        });
    }

    @Override
    public void onHouse(House house) {
        HouseRepository houseRepository = new HouseRepository((Application) context.getApplicationContext());
        houseRepository.insert(house);
    }

    @Override
    public void onTenant(Tenant tenant) {
        TenantRepository tenantRepository = new TenantRepository((Application) context.getApplicationContext());
        tenantRepository.insert(tenant);
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

    protected void send(List<TextInput> textInputs, Request request, NetworkCallback callback) {
        validateForm(textInputs);
        doesHaveInternetPermission();
        isInternetAvailable();
        network.send(request, callback);

    }

    protected void send(Request request, NetworkCallback callback) {
        doesHaveInternetPermission();
        isInternetAvailable();
        network.send(request, callback);
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
