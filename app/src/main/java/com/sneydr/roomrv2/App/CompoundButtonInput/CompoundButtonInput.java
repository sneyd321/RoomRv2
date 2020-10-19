package com.sneydr.roomrv2.App.CompoundButtonInput;

import android.view.View;
import android.widget.CompoundButton;


public abstract class CompoundButtonInput {


    protected CompoundButton compoundButton;
    protected View view;

    public CompoundButtonInput(View view, int compoundButton) {
        this.view = view;
        this.compoundButton = this.view.findViewById(compoundButton);

    }

    public boolean getChecked() {
        return this.compoundButton.isChecked();
    }


    public String getText() {
        return compoundButton.getText().toString();
    }

    public CompoundButton getCompoundButton() {
        return this.compoundButton;
    }




}
