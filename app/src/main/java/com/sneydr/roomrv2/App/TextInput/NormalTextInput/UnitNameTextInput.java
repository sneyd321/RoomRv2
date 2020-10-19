package com.sneydr.roomrv2.App.TextInput.NormalTextInput;

import android.view.View;

import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.App.Validation.EmptyStringValidator;
import com.sneydr.roomrv2.App.Validation.StringTooLongValidator;
import com.sneydr.roomrv2.App.Validation.StringTooShortValidator;


public class UnitNameTextInput extends TextInput {
    public UnitNameTextInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getValidationFacade().addValidator(new EmptyStringValidator("Please enter an unit name."));
        getValidationFacade().addValidator(new StringTooLongValidator(
                "Please enter a unit name shorter than 50 characters.", 50));
        getValidationFacade().addValidator(new StringTooShortValidator(
                "Please enter a unit name longer than 1 characters.", 1));

    }
}
