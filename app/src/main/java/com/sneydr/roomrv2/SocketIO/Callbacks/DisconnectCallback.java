package com.sneydr.roomrv2.SocketIO.Callbacks;

import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.SocketIO.Observables.DisconnectObservable;
import com.sneydr.roomrv2.SocketIO.Observers.DisconnectObserver;

import java.util.List;

public class DisconnectCallback extends SocketCallback implements DisconnectObservable {
    @Override
    public void call(Object... args) {
        String string = (String) args[0];
        Message message = jsonParser.parseMessage(string);
        notify(message);
    }

    @Override
    public void notify(Message message) {
        DisconnectObserver disconnectObserver = (DisconnectObserver) observer;
        disconnectObserver.onDisconnect(message);
    }
}
