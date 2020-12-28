package com.sneydr.roomrv2.SocketIO.Callbacks;

import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.SocketIO.Observables.MessageObservable;
import com.sneydr.roomrv2.SocketIO.Observers.MessageObserver;

public class MessageCallback extends SocketCallback implements MessageObservable {


    @Override
    public void call(Object... args) {
        String string = (String) args[0];
        Message message = jsonParser.parseMessage(string);
        notify(message);
    }


    @Override
    public void notify(Message message) {
        MessageObserver messageObserver = (MessageObserver) this.observer;
        messageObserver.onMessage(message);
    }
}
