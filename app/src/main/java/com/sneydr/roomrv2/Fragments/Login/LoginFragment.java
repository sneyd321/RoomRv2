package com.sneydr.roomrv2.Fragments.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.sneydr.roomrv2.Activities.MainActivityLandlord;
import com.sneydr.roomrv2.Activities.MainActivityTenant;
import com.sneydr.roomrv2.App.CompoundButtonInput.CompoundButtonInput;

import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.EmailTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PasswordTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Fragments.FragmentTemplate;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Network.Observers.TenantObserver;
import com.sneydr.roomrv2.Network.Observers.TenantsObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.Repositories.HomeownerRepository;
import com.sneydr.roomrv2.Repositories.TenantRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import okhttp3.Request;

public class LoginFragment extends FragmentTemplate implements HomeownerObserver, TenantObserver {



    private Button btnLogin;
    private Button btnSignup;

    private TextInput email, password;
    private CompoundButtonInput userType;
    private List<TextInput> textInputs;
    private HomeownerRepository homeownerRepository;
    private TenantRepository tenantRepository;


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
        homeownerRepository = new HomeownerRepository(this);
        tenantRepository = new TenantRepository(this);
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
            if (userType.getText().equals("Homeowner")){
                homeownerRepository.loginHomeowner(login);
            }
            else if (userType.getText().equals("Tenant")){
                tenantRepository.loginTenant(login);
            }
        }
    };

    @Override
    public void onHomeowner(Homeowner homeowner) {
        Intent intent = new Intent(getActivity(), MainActivityLandlord.class);
        intent.putExtra("homeownerId", homeowner.getHomeownerId());
        startActivity(intent);
    }

    @Override
    public void onTenant(Tenant tenant) {
        Intent intent = new Intent(getActivity(), MainActivityTenant.class);
        intent.putExtra("tenantId", tenant.getTenantId());
        startActivity(intent);
    }




}
