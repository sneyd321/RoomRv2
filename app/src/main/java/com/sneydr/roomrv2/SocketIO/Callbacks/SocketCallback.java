package com.sneydr.roomrv2.SocketIO.Callbacks;

import com.github.nkzawa.emitter.Emitter;
import com.sneydr.roomrv2.Network.ReadJson.JSONParser;
import com.sneydr.roomrv2.SocketIO.Observables.SocketIOObservable;
import com.sneydr.roomrv2.SocketIO.Observers.SocketIOObserver;

public abstract class SocketCallback implements SocketIOObservable, Emitter.Listener {

    protected SocketIOObserver observer;
    protected JSONParser jsonParser;

    public SocketCallback() {
        this.jsonParser = JSONParser.getInstance();
    }

    @Override
    public void registerObserver(SocketIOObserver observer) {
        this.observer = observer;
    }

    @Override
    public void clearObserver() {
        this.observer = null;
    }




}
