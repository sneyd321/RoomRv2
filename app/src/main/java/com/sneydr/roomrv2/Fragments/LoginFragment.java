package com.sneydr.roomrv2.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sneydr.roomrv2.Activities.MainActivityLandlord;

import com.sneydr.roomrv2.App.TextInput.NormalTextInput.EmailTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PasswordTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.HomeownerViewModel;
import com.sneydr.roomrv2.databinding.FragmentLoginBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.PagerAdapter;

public class LoginFragment extends FragmentTemplate implements HomeownerObserver {




    private FragmentLoginBinding binding;
    private Login login;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding =  DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        binding.btnLoginLogin.setOnClickListener(onLogin);
        binding.btnLoginSignup.setOnClickListener(onSignUp);
        Activity activity = getActivity();
        if (activity != null) {
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            String email = sharedPref.getString("email", "");
            String password = sharedPref.getString("password", "");
            login = new Login(email, password);
            if (!login.validateEmail()) {
                YoYo.with(Techniques.Shake).duration(1000).playOn(binding.edtLoginEmail);
                return binding.getRoot();
            }
            if (!login.validatePassword()) {
                YoYo.with(Techniques.Shake).duration(1000).playOn(binding.edtLoginPassword);
                return binding.getRoot();
            }
            ViewModelProviders.of(this).get(HomeownerViewModel.class).login(login, this);
        }
        return binding.getRoot();
    }

    private View.OnClickListener onSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("authToken", "");
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_signUpStatePagerFragment, bundle);
        }
    };





    View.OnClickListener onLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login = new Login(binding.edtLoginEmail.getText().toString(), binding.edtLoginPassword.getText().toString());

            ViewModelProviders.of(LoginFragment.this).get(HomeownerViewModel.class).login(login, LoginFragment.this);
        }
    };




    @Override
    public void onHomeowner(Homeowner homeowner) {
        Intent intent = new Intent(getActivity(), MainActivityLandlord.class);
        intent.putExtra("authToken", homeowner.getAuthToken());
        intent.putExtra("email", login.getEmail());
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("email", login.getEmail());
        editor.putString("password", login.getPassword());
        editor.apply();

        startActivity(intent);
    }

}
