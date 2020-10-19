package com.sneydr.roomrv2.Adapters;

import com.sneydr.roomrv2.R;

public class ApprovedState implements ButtonState {



    public ApprovedState(ButtonStateContext context) {
        context.setState(this);
    }


    @Override
    public int getColour() {
        return R.color.Green;
    }

    @Override
    public int getBackgroundDrawable() {
        return R.drawable.approved_tenant_background;
    }

    @Override
    public String getText() {
        return "Approved";
    }
}