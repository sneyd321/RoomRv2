package com.sneydr.roomrv2.App.Naviagation;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class Navigation {



    private static Navigation instance;

    public static Navigation getInstance() {
        if (instance == null) {
            instance = new Navigation();
        }
        return instance;
    }


    public void navigateBack(Fragment fragment) {
        try {
            NavHostFragment.findNavController(fragment).navigateUp();
        }
        catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public void navigate(Fragment fragment, int to) {
        try {
            NavHostFragment.findNavController(fragment).navigate(to);
        }
        catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public void navigate(Fragment fragment, int to, String authToken) {
        Bundle bundle = new Bundle();
        bundle.putString("authToken", authToken);
        try {
            NavHostFragment.findNavController(fragment).navigate(to, bundle);
        }
        catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }


    public void navigate(Fragment fragment, int to, String authToken, int houseId) {
        Bundle bundle = new Bundle();
        bundle.putString("authToken", authToken);
        bundle.putInt("houseId", houseId);
        try {
            NavHostFragment.findNavController(fragment).navigate(to, bundle);
        }
        catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    public void navigate(Fragment fragment, int to, String authToken, int houseId, String email) {
        Bundle bundle = new Bundle();
        bundle.putString("authToken", authToken);
        bundle.putInt("houseId", houseId);
        bundle.putString("email", email);
        try {
            NavHostFragment.findNavController(fragment).navigate(to, bundle);
        }
        catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }


    public void navigate(Fragment fragment, int to, String authToken, int houseId, int problemId) {
        Bundle bundle = new Bundle();
        bundle.putString("authToken", authToken);
        bundle.putInt("houseId", houseId);
        bundle.putInt("problemId", problemId);
        try {
            NavHostFragment.findNavController(fragment).navigate(to, bundle);
        }
        catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }
}
