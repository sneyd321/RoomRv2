package com.sneydr.roomrv2.App.TextInput.NormalTextInput;

import android.view.View;

import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.App.Validation.EmptyStringValidator;
import com.sneydr.roomrv2.App.Validation.StringTooLongValidator;
import com.sneydr.roomrv2.App.Validation.StringTooShortValidator;


public class PhoneNumberTextInput extends TextInput {
    public PhoneNumberTextInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getValidationFacade().addValidator(new EmptyStringValidator("Please enter a phone number."));
        getValidationFacade().addValidator(new StringTooLongValidator("Please enter a phone number shorter than 15 characters.", 15));
        getValidationFacade().addValidator(new StringTooShortValidator("Please enter a password longer than 10 characters.", 10));

    }
}
