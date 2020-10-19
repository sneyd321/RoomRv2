package com.sneydr.roomrv2.App.TextInput.NumberTextInput;

import android.view.View;

import com.sneydr.roomrv2.App.Validation.IntTooHighValidator;
import com.sneydr.roomrv2.App.Validation.IntTooLowValidator;
import com.sneydr.roomrv2.App.Validation.IntZeroValidator;


public class RentAmountNumberInput extends NumberTextInput {

    public RentAmountNumberInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getValidationFacade().addValidator(new IntZeroValidator("Please enter a rent amount."));
        getValidationFacade().addValidator(new IntTooHighValidator(
                "Please enter an amount smaller than 5000.", 5000));
        getValidationFacade().addValidator(new IntTooLowValidator(
                "Please enter an amount larger than 0.", 0));

    }


}
