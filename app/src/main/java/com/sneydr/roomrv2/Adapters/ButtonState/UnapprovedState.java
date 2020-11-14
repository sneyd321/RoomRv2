package com.sneydr.roomrv2.Adapters.ButtonState;

import com.sneydr.roomrv2.Adapters.ButtonState.ButtonState;
import com.sneydr.roomrv2.Adapters.ButtonState.ButtonStateContext;
import com.sneydr.roomrv2.R;

public class UnapprovedState implements ButtonState {


    public UnapprovedState(ButtonStateContext context) {
        context.setState(this);
    }

    @Override
    public int getColour() {
        return R.color.Red;
    }

    @Override
    public int getBackgroundDrawable() {
        return R.drawable.unnapproved_tenant_background;
    }

    @Override
    public String getText() {
        return "Unapproved";
    }
}
