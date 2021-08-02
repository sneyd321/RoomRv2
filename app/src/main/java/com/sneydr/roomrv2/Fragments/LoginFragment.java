package com.sneydr.roomrv2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
        startActivity(intent);
    }

}
