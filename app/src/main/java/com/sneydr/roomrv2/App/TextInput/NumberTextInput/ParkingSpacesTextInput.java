package com.sneydr.roomrv2.App.TextInput.NumberTextInput;

import android.view.View;

import com.sneydr.roomrv2.App.Validation.IntTooHighValidator;
import com.sneydr.roomrv2.App.Validation.IntTooLowValidator;
import com.sneydr.roomrv2.App.Validation.IntZeroValidator;


public class ParkingSpacesTextInput extends NumberTextInput {
    public ParkingSpacesTextInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getValidationFacade().addValidator(new IntZeroValidator("Please enter the number of parking spaces."));
        getValidationFacade().addValidator(new IntTooHighValidator(
                "Please enter an amount smaller than 10.", 10));
        getValidationFacade().addValidator(new IntTooLowValidator(
                "Please enter an amount larger than 0.", 0));
    }




}
