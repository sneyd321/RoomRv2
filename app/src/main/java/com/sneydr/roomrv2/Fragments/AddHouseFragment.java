package com.sneydr.roomrv2.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sneydr.roomrv2.App.CompoundButtonInput.AnimatedCompoundButtonInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.AnimatedSelectedNotSelectedSwitchInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.AnimatedTenantHomeownerSwitchInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.NormalCheckboxInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.SelectedNotSelectedSwitchInput;
import com.sneydr.roomrv2.App.TextInput.AutoCompleteTextView.AddressAutoCompleteTextInput;
import com.sneydr.roomrv2.App.TextInput.AutoCompleteTextView.CityAutoCompleteTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PostalCodeTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.RentMadePayableTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.UnitNameTextInput;
import com.sneydr.roomrv2.App.TextInput.NumberTextInput.NumberTextInput;
import com.sneydr.roomrv2.App.TextInput.NumberTextInput.ParkingAmountNumberTextInput;
import com.sneydr.roomrv2.App.TextInput.NumberTextInput.ParkingSpacesTextInput;
import com.sneydr.roomrv2.App.TextInput.NumberTextInput.RentAmountNumberInput;
import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.Entities.House.Amenity.AdditionalStorageAmenity;
import com.sneydr.roomrv2.Entities.House.Amenity.AirConditioningAmenity;
import com.sneydr.roomrv2.Entities.House.Amenity.Amenity;
import com.sneydr.roomrv2.Entities.House.Amenity.GasAmenity;
import com.sneydr.roomrv2.Entities.House.Amenity.GuestParkingAmenity;
import com.sneydr.roomrv2.Entities.House.Amenity.OnSiteLaundryAmenity;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Entities.RentDetails.CashPaymentOption;
import com.sneydr.roomrv2.Entities.RentDetails.ChequePaymentOption;
import com.sneydr.roomrv2.Entities.RentDetails.CreditPaymentOption;
import com.sneydr.roomrv2.Entities.RentDetails.RentDetails;
import com.sneydr.roomrv2.Entities.House.Utility.ElectricityUtility;
import com.sneydr.roomrv2.Entities.House.Utility.HeatUtility;
import com.sneydr.roomrv2.Entities.House.Utility.Utility;
import com.sneydr.roomrv2.Entities.House.Utility.WaterUtility;
import com.sneydr.roomrv2.R;

import java.util.ArrayList;
import java.util.List;


public class AddHouseFragment extends FragmentTemplate {

    private TextInput address, city, postalCode, unitName, rentMadePayable;
    private NumberTextInput parkingSpaces, parkingAmount, rentAmount;
    private SelectedNotSelectedSwitchInput isCondo, swtAirConditioning, swtGuestParking, swtLaundry, swtGas, swtStorage, swtWater, swtHeat, swtElectricity;
    private RadioButtonCompoundButtonInput rentDueDate;
    private NormalCheckboxInput chkPaymentMethod1, chkPaymentMethod3;
    private AnimatedCompoundButtonInput chkPaymentMethod2;
    private AnimatedSelectedNotSelectedSwitchInput swtParking;






    protected void initUI(View view) {
        List<TextInput> textInputs = new ArrayList<>();
        address = new AddressAutoCompleteTextInput(view, R.id.tilAddHouseAddress, R.id.edtAddHouseAddress);
        city = new CityAutoCompleteTextInput(view, R.id.tilAddHouseCity, R.id.edtAddHouseCity);
        postalCode = new PostalCodeTextInput(view, R.id.tilAddHousePostalCode, R.id.edtAddHousePostalCode);
        unitName = new UnitNameTextInput(view, R.id.tilAddHouseUnitName, R.id.edtAddHouseUnitType);
        parkingSpaces = new ParkingSpacesTextInput(view, R.id.tilAddHouseParkingSpaces, R.id.edtAddHouseParkingSpaces);
        rentMadePayable = new RentMadePayableTextInput(view, R.id.tilAddHouseRentMadePayable, R.id.edtAddHouseRentMadePayable);
        rentAmount = new RentAmountNumberInput(view, R.id.tilAddHouseRentAmount, R.id.edtAddHouseRentAmount);
        parkingAmount = new ParkingAmountNumberTextInput(view, R.id.tilAddHouseParkingAmount, R.id.edtAddHouseParkingAmount);
        textInputs.add(address);
        textInputs.add(city);
        textInputs.add(postalCode);
        textInputs.add(unitName);
        textInputs.add(parkingSpaces);
        textInputs.add(rentMadePayable);
        textInputs.add(rentAmount);
        textInputs.add(parkingAmount);

        isCondo = new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseIsCondo);
        rentDueDate = new RadioButtonCompoundButtonInput(view, 0, R.id.rdgAddHouseRentDueDate);
        chkPaymentMethod1 =  new NormalCheckboxInput(view, R.id.chkAddHouseRentPaymentMethod1);
        chkPaymentMethod2 =  new AnimatedCompoundButtonInput(view, R.id.chkAddHouseRentPaymentMethod2, R.id.tilAddHouseRentMadePayable);
        chkPaymentMethod3 =  new NormalCheckboxInput(view, R.id.chkAddHouseRentPaymentMethod3);
        swtAirConditioning =  new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseAirConditioning);
        swtParking = new AnimatedSelectedNotSelectedSwitchInput(view, R.id.swtAddHouseParking, R.id.tilAddHouseParkingAmount);
        swtGas = new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseGas);
        swtGuestParking = new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseGuestParking);
        swtStorage =  new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseStorage);
        swtLaundry =  new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseLaundry);
        swtWater = new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseWaterUtility);
        swtHeat = new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseHeatUtility);
        swtElectricity = new SelectedNotSelectedSwitchInput(view, R.id.swtAddHouseElectricityUtility);

        Button btnAddHouse = view.findViewById(R.id.btnAddHouse);
        btnAddHouse.setOnClickListener(onAddHouse);
        Button btnBack = view.findViewById(R.id.btnAddHouseBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_house, container, false);
        initUI(view);
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

    private RentDetails getRentDetail() {
        RentDetails rentDetails = new RentDetails(rentDueDate.getText(), rentAmount.getNumber(), parkingAmount.getNumber());
        if (chkPaymentMethod1.getChecked()){
            rentDetails.addPaymentOption(new CashPaymentOption());
        }
        if (chkPaymentMethod2.getChecked()) {
            rentDetails.addPaymentOption(new ChequePaymentOption(rentMadePayable.getText()));
        }
        if (chkPaymentMethod3.getChecked()) {
            rentDetails.addPaymentOption(new CreditPaymentOption());
        }
        return rentDetails;
    }

    private List<Amenity> getAmenities() {
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(new AirConditioningAmenity(swtAirConditioning.getChecked()));
        amenities.add(new AdditionalStorageAmenity(swtStorage.getChecked()));
        amenities.add(new GasAmenity(swtGas.getChecked()));
        amenities.add(new GuestParkingAmenity(swtGuestParking.getChecked()));
        amenities.add(new OnSiteLaundryAmenity(swtLaundry.getChecked()));
        return amenities;
     }

     private List<Utility> getUtilities() {
        List<Utility> utilities = new ArrayList<>();
        utilities.add(new WaterUtility(swtWater.getText()));
        utilities.add(new HeatUtility(swtHeat.getText()));
        utilities.add(new ElectricityUtility(swtElectricity.getText()));
        return utilities;
     }

    private View.OnClickListener onAddHouse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            House house = new House(getRentalUnitLocation(), getRentDetail());
            house.setAmenities(getAmenities());
            house.setUtilities(getUtilities());
            //network.postHouse(house);
        }
    };
}
