package com.sneydr.roomrv2.Adapters.ButtonState;

import com.sneydr.roomrv2.Adapters.ButtonState.ButtonState;

public class ButtonStateContext {

    private ButtonState state;

    public ButtonStateContext(){
        state = null;
    }

    public void setState(ButtonState state){
        this.state = state;
    }

    public ButtonState getState(){
        return state;
    }

}
