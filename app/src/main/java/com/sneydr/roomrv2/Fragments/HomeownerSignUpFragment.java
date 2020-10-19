package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sneydr.roomrv2.App.TextInput.AutoCompleteTextView.AddressAutoCompleteTextInput;
import com.sneydr.roomrv2.App.TextInput.AutoCompleteTextView.CityAutoCompleteTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.EmailTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.FirstNameTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.LastNameTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PasswordTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PhoneNumberTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PoBoxTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PostalCodeTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.UnitNameTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Location.HomeownerLocation;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeownerSignUpFragment extends Fragment {


    private TextInput firstName, lastName, email, phoneNumber, password, address, city, postalCode, poBox, unitNumber;
    private List<TextInput> textInputs;
    private void initUI(View view) {
        textInputs = new ArrayList<>();
        firstName = new FirstNameTextInput(view, R.id.tilLSUFirstName, R.id.edtLSUFirstName);
        lastName = new LastNameTextInput(view, R.id.tilLSULastName, R.id.edtLSULastName);
        email = new EmailTextInput(view, R.id.tilLSUEmail, R.id.edtLSUEmail);
        phoneNumber = new PhoneNumberTextInput(view, R.id.tilLSUEmail, R.id.edtLSUPhoneNumber);
        password = new PasswordTextInput(view, R.id.tilLSUPassword, R.id.edtLSUPassword);
        address = new AddressAutoCompleteTextInput(view, R.id.tilAddHouseHomeOwnerAddress, R.id.edtAddHouseHomeownerAddress);
        city = new CityAutoCompleteTextInput(view, R.id.tilAddHouseHomeownerCity, R.id.edtAddHouseHomeownerCity);
        postalCode = new PostalCodeTextInput(view, R.id.tilAddHouseHomeownerPostalCode, R.id.edtAddHouseHomeownerPostalCode);
        poBox = new PoBoxTextInput(view, R.id.tilAddHousePOBox, R.id.edtAddHousePOBox);
        unitNumber = new UnitNameTextInput(view, R.id.tilAddHouseUnitNumber, R.id.edtAddHouseUnitNumber);
        textInputs.add(firstName);
        textInputs.add(lastName);
        textInputs.add(email);
        textInputs.add(password);
        textInputs.add(phoneNumber);
        textInputs.add(address);
        textInputs.add(city);
        textInputs.add(postalCode);
        textInputs.add(poBox);
        textInputs.add(unitNumber);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homeowner_sign_up, container, false);
        initUI(view);
        return view;
    }

    public List<TextInput> getTextInputs() {
        return textInputs;
    }

    public Homeowner getHomeowner() {
        return new Homeowner(
                firstName.getText(),
                lastName.getText(),
                email.getText(),
                password.getText(),
                phoneNumber.getText(),
                new HomeownerLocation(
                        address.getText(),
                        city.getText(),
                        "Ontario",
                        postalCode.getText(),
                        poBox.getText(),
                        unitNumber.getText()
                )
        );
    }






}
