package com.sneydr.roomrv2.App.CompoundButtonInput;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RadioButtonCompoundButtonInput extends CompoundButtonInput {

    private RadioGroup radioGroup;

    public RadioButtonCompoundButtonInput(View view, int compoundButton, int radioGroup) {
        super(view, compoundButton);
        this.radioGroup = view.findViewById(radioGroup);
    }


    @Override
    public String getText() {
        RadioButton selectedRadioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        return selectedRadioButton.getText().toString();
    }
}
