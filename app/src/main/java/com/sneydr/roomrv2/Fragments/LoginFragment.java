package com.sneydr.roomrv2.Fragments;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.sneydr.roomrv2.Activities.LoginActivity;
import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.Activities.MainActivityTenant;
import com.sneydr.roomrv2.App.CompoundButtonInput.CompoundButtonInput;

import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.EmailTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PasswordTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Database.Homeowner.HomeownerRepository;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.R;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import okhttp3.Request;

public class LoginFragment extends FragmentTemplate  {



    private Button btnLogin;
    private Button btnSignup;

    private TextInput email, password;
    private CompoundButtonInput userType;
    private List<TextInput> textInputs;


    protected void initUI(View view) {
        textInputs = new ArrayList<>();
        email = new EmailTextInput(view, R.id.tilLoginEmail, R.id.edtLoginEmail);
        password = new PasswordTextInput(view, R.id.tilLoginPassword, R.id.edtLoginPassword);
        textInputs.add(email);
        textInputs.add(password);
        userType = new RadioButtonCompoundButtonInput(view, 0, R.id.rdgLogin);
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





    private View.OnClickListener onSignUp = view -> NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_signUpStatePagerFragment);


    View.OnClickListener onLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Login login = new Login(email.getText(), password.getText());
            Intent intent = new Intent(getActivity(), MainActivityTenant.class);
            startActivity(intent);
            /*
            if (userType.getText().equals("Homeowner")){
                Request request = network.loginHomeowner(login);
                NetworkCallback callback = network.getCallback(NetworkCallbackType.GetHomeowner);
                callback.registerObserver(LoginFragment.this);
                send(textInputs, request, callback);

            }
            else if (userType.getText().equals("Tenant")){
                Request request = network.loginTenant(login);
                NetworkCallback callback = network.getCallback(NetworkCallbackType.GetTenant);
                callback.registerObserver(LoginFragment.this);
                send(textInputs, request, callback);
            }

            */
        }
    };

    @Override
    public void onHomeowner(Homeowner homeowner) {
        super.onHomeowner(homeowner);
        Intent intent = new Intent(getActivity(), MainActivityLandlord.class);
        startActivity(intent);
    }

    @Override
    public void onTenant(Tenant tenant) {
        super.onTenant(tenant);
        Intent intent = new Intent(getActivity(), MainActivityTenant.class);
        startActivity(intent);
    }




}
