package com.sneydr.roomrv2.App.CompoundButtonInput;

import android.view.View;
import android.widget.CompoundButton;

public class SelectedNotSelectedSwitchInput extends CompoundButtonInput {

    public SelectedNotSelectedSwitchInput(View view, int compoundButton) {
        super(view, compoundButton);
        this.compoundButton.setOnCheckedChangeListener(onSwapText);
    }

    private CompoundButton.OnCheckedChangeListener onSwapText = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            String text = b ? "Selected" : "Not Selected";
            compoundButton.setText(text);
        }
    };


}
