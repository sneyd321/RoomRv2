package com.sneydr.roomrv2.App.TextInput.NormalTextInput;

import android.view.View;

import com.sneydr.roomrv2.App.TextInput.TextInput;
import com.sneydr.roomrv2.App.Validation.EmptyStringValidator;
import com.sneydr.roomrv2.App.Validation.StringTooLongValidator;
import com.sneydr.roomrv2.App.Validation.StringTooShortValidator;


public class EmailTextInput extends TextInput {
    public EmailTextInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getValidationFacade().addValidator(new EmptyStringValidator("Please enter an email."));
        getValidationFacade().addValidator(new StringTooLongValidator("Please enter an email shorter than 50 characters.", 50));
        getValidationFacade().addValidator(new StringTooShortValidator("Please enter an email longer than 3 characters.", 3));

    }

    @Override
    public String getText() {
        return getEditText().getText().toString();
    }
}
