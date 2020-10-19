package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sneydr.roomrv2.App.TextInput.NormalTextInput.EmailTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.FirstNameTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.LastNameTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PasswordTextInput;
import com.sneydr.roomrv2.App.TextInput.NumberTextInput.HouseIdNumberTextInput;
import com.sneydr.roomrv2.App.TextInput.NumberTextInput.NumberTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TenantSignUpFragment extends Fragment {


    private TextInput firstName, lastName, email, password;
    private NumberTextInput houseId;
    private List<TextInput> textInputs;


    private void initUI(View view) {
        textInputs = new ArrayList<>();
        firstName = new FirstNameTextInput(view, R.id.tilTSUFirstName, R.id.edtTSUFirstName);
        lastName = new LastNameTextInput(view, R.id.tilTSULastName, R.id.edtTSULastName);
        email = new EmailTextInput(view, R.id.tilTSUEmail, R.id.edtTSUEmail);
        houseId = new HouseIdNumberTextInput(view, R.id.tilTSUHouseId, R.id.edtTSUHouseId);
        password = new PasswordTextInput(view, R.id.tilTSUPassword, R.id.edtTSUPassword);
        textInputs.add(firstName);
        textInputs.add(lastName);
        textInputs.add(email);
        textInputs.add(houseId);
        textInputs.add(password);
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenant_sign_up, container, false);
        initUI(view);
        return view;
    }

    public Tenant getTenant() {
        return new Tenant(
                firstName.getText(),
                lastName.getText(),
                email.getText(),
                password.getText(),
                houseId.getNumber()
        );
    }

    public List<TextInput> getTextInputs() {
        return this.textInputs;
    }

}
