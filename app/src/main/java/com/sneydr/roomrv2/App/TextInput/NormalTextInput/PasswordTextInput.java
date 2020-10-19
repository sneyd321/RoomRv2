package com.sneydr.roomrv2.App.TextInput.NormalTextInput;

import android.view.View;

import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.App.Validation.EmptyStringValidator;
import com.sneydr.roomrv2.App.Validation.StringTooLongValidator;
import com.sneydr.roomrv2.App.Validation.StringTooShortValidator;


public class PasswordTextInput extends TextInput {
    public PasswordTextInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getValidationFacade().addValidator(new EmptyStringValidator("Please enter a password."));
        getValidationFacade().addValidator(new StringTooLongValidator("Please enter a password shorter than 50 characters.", 50));
        getValidationFacade().addValidator(new StringTooShortValidator("Please enter a password longer than 6 characters.", 6));

    }

    @Override
    public String getText() {
        return getEditText().getText().toString();
    }
}
