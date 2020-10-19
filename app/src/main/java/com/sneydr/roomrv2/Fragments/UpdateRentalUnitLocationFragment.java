package com.sneydr.roomrv2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.sneydr.roomrv2.App.CompoundButtonInput.CompoundButtonInput;

import com.sneydr.roomrv2.App.TextInput.NumberTextInput.NumberTextInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Network.NetworkObserver;
import com.sneydr.roomrv2.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UpdateRentalUnitLocationFragment extends FragmentTemplate implements NetworkObserver {

    TextInput address, city, postalCode, unitName;
    NumberTextInput parkingSpaces;
    CompoundButtonInput isCondo;

    Button btnUpdateLocation, btnBack;

    private List<TextInput> textInputs;

    protected void initUI(View view){
        textInputs = new ArrayList<>();
        //address = textInputFactory.getTextInput(R.id.edtAddHouseAddress);
        //address.getEditText().setText(house.getRentalUnitLocation().getFormattedPrimaryAddress());
        //city = textInputFactory.getTextInput(R.id.edtAddHouseCity);
        //city.getEditText().setText(house.getRentalUnitLocation().getCity());
        //postalCode = textInputFactory.getTextInput(R.id.edtAddHousePostalCode);
        //postalCode.getEditText().setText(house.getRentalUnitLocation().getPostalCode());
        //unitName = textInputFactory.getTextInput(R.id.edtAddHouseUnitType);
        //unitName.getEditText().setText(house.getRentalUnitLocation().getUnitName());
        //parkingSpaces = textInputFactory.getNumberTextInput(R.id.edtAddHouseParkingSpaces);
        //parkingSpaces.getEditText().setText(Integer.toString(house.getRentalUnitLocation().getParkingSpaces()));



        //isCondo = compoundButtonFactory.getSelectedNotSelectedSwitchInput(R.id.swtAddHouseIsCondo);
        //isCondo.getCompoundButton().setChecked(house.getRentalUnitLocation().isCondo());

        btnUpdateLocation = view.findViewById(R.id.btnUpdateHouseLocation);
        btnUpdateLocation.setOnClickListener(onUpdateLocation);
        btnBack = view.findViewById(R.id.btnUpdateHouseLocationBack);
        btnBack.setOnClickListener(onBack);

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_house_detail_update_location, container, false);
        return view;
    }


    private RentalUnitLocation getRentalUnitLocation() {
        return new RentalUnitLocation(
                address.getText(),
                city.getText(),
                "Ontario",
                postalCode.getText(),
                unitName.getText(),
                isCondo.getChecked(),
                parkingSpaces.getNumber()
        );
    }


    private View.OnClickListener onBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().onBackPressed();
        }
    };


    private View.OnClickListener onUpdateLocation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //network.put(house, "House/" + house.getHouseId());
        }
    };




}
