package com.sneydr.roomrv2.App.TextInput.NumberTextInput;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.sneydr.roomrv2.App.TextInput.TextInput;

import com.sneydr.roomrv2.R;

public abstract  class NumberTextInput extends TextInput {



    public NumberTextInput(View view, int layoutId, int editTextId) {
        super(view, layoutId, editTextId);
        getLayout().setEndIconOnClickListener(this.onClear);
        getEditText().setOnFocusChangeListener(this.onFocusChangeListener);
        getEditText().removeTextChangedListener(super.textWatcher);
        getEditText().addTextChangedListener(this.textWatcher);
    }


    View.OnClickListener onClear = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!getEditText().hasFocus()) {
                getEditText().setText("0");
                return;
            }
            getEditText().setText("");
            getLayout().setError(null);
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    public int getNumber() {
        try {
            int number = Integer.parseInt(getEditText().getText().toString());
            return number;
        }
        catch (NumberFormatException ex) {
            return 0;
        }

    }

    @Override
    public void invokeValidation() {
        getLayout().setError(getValidationFacade().validate(getNumber()));
    }

    EditText.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            //If edittext does not have focus
            if (!b) {
                getLayout().setErrorEnabled(true);
                return;

            }
            getLayout().setErrorEnabled(false);


        }
    };


}
