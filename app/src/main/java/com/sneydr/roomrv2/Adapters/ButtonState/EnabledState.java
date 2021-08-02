package com.sneydr.roomrv2.Adapters.ButtonState;

import android.content.Context;

import com.sneydr.roomrv2.R;

public class EnabledState implements ButtonState {
    @Override
    public int getColour() {
        return R.color.colorPrimary;
    }

    @Override
    public int getBackgroundDrawable() {
        return R.color.colorPrimary;
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
        return true;
    }


}
