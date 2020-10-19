package com.sneydr.roomrv2.App.CompoundButtonInput;

import android.view.View;
import android.widget.CompoundButton;

public class TenantHomeownerSwitchInput extends CompoundButtonInput {



    public TenantHomeownerSwitchInput(View view, int compoundButton) {
        super(view, compoundButton);
        this.compoundButton.setOnCheckedChangeListener(onSwapText);

    }

    private CompoundButton.OnCheckedChangeListener onSwapText = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            String text = b ? "Homeowner" : "Tenant";
            compoundButton.setText(text);
        }
    };



}
