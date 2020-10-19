package com.sneydr.roomrv2.Adapters;

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
