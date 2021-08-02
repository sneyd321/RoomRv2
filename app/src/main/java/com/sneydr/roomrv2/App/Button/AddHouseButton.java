package com.sneydr.roomrv2.App.Button;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.sneydr.roomrv2.App.Naviagation.Navigation;
import com.sneydr.roomrv2.R;

public class AddHouseButton implements View.OnClickListener {


    private String authToken;
    private Fragment fragment;

    public AddHouseButton(Fragment fragment, String authToken) {
        this.fragment = fragment;
        this.authToken = authToken;
    }

    @Override
    public void onClick(View v) {
        Navigation navigation = Navigation.getInstance();
        navigation.navigate(fragment, R.id.action_housesFragment_to_addHouseWebFragment, authToken);
    }
}
