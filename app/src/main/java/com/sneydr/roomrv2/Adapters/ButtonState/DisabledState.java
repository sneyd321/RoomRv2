package com.sneydr.roomrv2.Adapters.ButtonState;

import com.sneydr.roomrv2.R;

public class DisabledState implements ButtonState {
    @Override
    public int getColour() {
        return R.color.LightGray;
    }

    @Override
    public int getBackgroundDrawable() {
        return R.color.LightGray;
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public int getVectorDrawable() {
        return 0;
    }

    @Override
    public boolean getEnabled() {
        return false;
    }


}
