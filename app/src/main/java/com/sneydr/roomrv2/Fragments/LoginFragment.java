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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class LoginFragment extends FragmentTemplate implements HomeownerObserver {



    private Button btnLogin;
    private Button btnSignup;
    private TextInput email, password;
    private List<TextInput> textInputs;
    private HomeownerViewModel homeownerViewModel;


    protected void initUI(View view) {
        textInputs = new ArrayList<>();
        homeownerViewModel = ViewModelProviders.of(this).get(HomeownerViewModel.class);
        email = new EmailTextInput(view, R.id.tilLoginEmail, R.id.edtLoginEmail);
        password = new PasswordTextInput(view, R.id.tilLoginPassword, R.id.edtLoginPassword);
        textInputs.add(email);
        textInputs.add(password);
        btnLogin = view.findViewById(R.id.btnLoginLogin);
        btnLogin.setOnClickListener(onLogin);
        btnSignup = view.findViewById(R.id.btnLoginSignup);
        btnSignup.setOnClickListener(onSignUp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initUI(view);
        return view;
    }

    private View.OnClickListener onSignUp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_signUpStatePagerFragment);
        }
    };





    View.OnClickListener onLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Login login = new Login(email.getText(), password.getText());
            homeownerViewModel.login(login, LoginFragment.this);
        }
    };

    @Override
    public void onHomeowner(Homeowner homeowner) {
        Intent intent = new Intent(getActivity(), MainActivityLandlord.class);
        intent.putExtra("authToken", homeowner.getAuthToken());
        startActivity(intent);
    }

}
