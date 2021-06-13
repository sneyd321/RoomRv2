package com.sneydr.roomrv2.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sneydr.roomrv2.App.CompoundButtonInput.AnimatedCompoundButtonInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.AnimatedSelectedNotSelectedSwitchInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.NormalCheckboxInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.RadioButtonCompoundButtonInput;
import com.sneydr.roomrv2.App.CompoundButtonInput.SelectedNotSelectedSwitchInput;
import com.sneydr.roomrv2.App.TextInput.AutoCompleteTextView.AddressAutoCompleteTextInput;
import com.sneydr.roomrv2.App.TextInput.AutoCompleteTextView.CityAutoCompleteTextInput;
import com.sneydr.roomrv2.App.TextInput.NormalTextInput.PoBoxTextInput;
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
import com.sneydr.roomrv2.Entities.House.Location.HomeownerLocation;
import com.sneydr.roomrv2.Entities.House.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Entities.House.RentDetails.CashPaymentOption;
import com.sneydr.roomrv2.Entities.House.RentDetails.ChequePaymentOption;
import com.sneydr.roomrv2.Entities.House.RentDetails.CreditPaymentOption;
import com.sneydr.roomrv2.Entities.House.RentDetails.RentDetails;
import com.sneydr.roomrv2.Entities.House.Utility.ElectricityUtility;
import com.sneydr.roomrv2.Entities.House.Utility.HeatUtility;
import com.sneydr.roomrv2.Entities.House.Utility.Utility;
import com.sneydr.roomrv2.Entities.House.Utility.WaterUtility;
import com.sneydr.roomrv2.Network.Observers.HouseObserver;
import com.sneydr.roomrv2.R;
import com.sneydr.roomrv2.ViewModels.HouseViewModel;

import java.util.ArrayList;
import java.util.List;


public class AddHouseFragment extends FragmentTemplate implements HouseObserver {

    private TextInput rentalUnitAddress, rentalUnitCity, rentalUnitPostalCode, unitName, rentMadePayable, homeownerAddress, homeownerCity, homeownerPostalCode, poBox, unitNumber;;
    private NumberTextInput parkingSpaces, parkingAmount, rentAmount;
    private SelectedNotSelectedSwitchInput isCondo, swtAirConditioning, swtGuestParking, swtLaundry, swtGas, swtStorage, swtWater, swtHeat, swtElectricity;
    private RadioButtonCompoundButtonInput rentDueDate;
    private NormalCheckboxInput chkPaymentMethod1, chkPaymentMethod3;
    private AnimatedCompoundButtonInput chkPaymentMethod2;
    private AnimatedSelectedNotSelectedSwitchInput swtParking;
    private List<TextInput> textInputs;
    private String authToken;
    private HouseViewModel houseViewModel;

    protected void initUI(View view) {
        textInputs = new ArrayList<>();
        rentalUnitAddress = new AddressAutoCompleteTextInput(view, R.id.tilAddHouseAddress, R.id.edtAddHouseAddress);
        rentalUnitCity = new CityAutoCompleteTextInput(view, R.id.tilAddHouseCity, R.id.edtAddHouseCity);
        rentalUnitPostalCode = new PostalCodeTextInput(view, R.id.tilAddHousePostalCode, R.id.edtAddHousePostalCode);
        unitName = new UnitNameTextInput(view, R.id.tilAddHouseUnitName, R.id.edtAddHouseUnitType);
        parkingSpaces = new ParkingSpacesTextInput(view, R.id.tilAddHouseParkingSpaces, R.id.edtAddHouseParkingSpaces);
        rentMadePayable = new RentMadePayableTextInput(view, R.id.tilAddHouseRentMadePayable, R.id.edtAddHouseRentMadePayable);
        rentAmount = new RentAmountNumberInput(view, R.id.tilAddHouseRentAmount, R.id.edtAddHouseRentAmount);
        parkingAmount = new ParkingAmountNumberTextInput(view, R.id.tilAddHouseParkingAmount, R.id.edtAddHouseParkingAmount);
        homeownerAddress = new AddressAutoCompleteTextInput(view, R.id.tilAddHouseHomeOwnerAddress, R.id.edtAddHouseHomeownerAddress);
        homeownerCity = new CityAutoCompleteTextInput(view, R.id.tilAddHouseHomeownerCity, R.id.edtAddHouseHomeownerCity);
        homeownerPostalCode = new PostalCodeTextInput(view, R.id.tilAddHouseHomeownerPostalCode, R.id.edtAddHouseHomeownerPostalCode);
        poBox = new PoBoxTextInput(view, R.id.tilAddHousePOBox, R.id.edtAddHousePOBox);
        unitNumber = new UnitNameTextInput(view, R.id.tilAddHouseUnitNumber, R.id.edtAddHouseUnitNumber);
        textInputs.add(rentalUnitAddress);
        textInputs.add(rentalUnitCity);
        textInputs.add(rentalUnitPostalCode);
        textInputs.add(unitName);
        textInputs.add(parkingSpaces);
        textInputs.add(rentMadePayable);
        textInputs.add(rentAmount);
        textInputs.add(parkingAmount);
        textInputs.add(homeownerAddress);
        textInputs.add(homeownerCity);
        textInputs.add(homeownerPostalCode);
        textInputs.add(poBox);
        textInputs.add(unitNumber);

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

        houseViewModel = ViewModelProviders.of(this).get(HouseViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_house, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("authToken")){
            authToken = bundle.getString("authToken");
            initUI(view);
        }
        return view;
    }

    private RentalUnitLocation getRentalUnitLocation() {
        return new RentalUnitLocation(
                rentalUnitAddress.getText(),
                rentalUnitCity.getText(),
                "Ontario",
                rentalUnitPostalCode.getText(),
                unitName.getText(),
                isCondo.getChecked(),
                parkingSpaces.getNumber()
        );
    }

    private HomeownerLocation getHomeownerLocation() {
        return new HomeownerLocation(
                homeownerAddress.getText(),
                homeownerCity.getText(),
                "Ontario",
                homeownerPostalCode.getText(),
                poBox.getText(),
                unitNumber.getText()
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
            /*
            House house = new House(authToken, getHomeownerLocation(), getRentalUnitLocation(), getRentDetail());
            house.setAmenities(getAmenities());
            house.setUtilities(getUtilities());
            houseViewModel.saveHouse(house, AddHouseFragment.this);
            */
        }

    };

    @Override
    public void onHouse(House house) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                NavHostFragment.findNavController(AddHouseFragment.this).popBackStack();
            }
        });
    }
}
