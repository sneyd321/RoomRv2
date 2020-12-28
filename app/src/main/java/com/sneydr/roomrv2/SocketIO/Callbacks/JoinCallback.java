package com.sneydr.roomrv2.SocketIO.Callbacks;

import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.SocketIO.Observables.JoinObservable;
import com.sneydr.roomrv2.SocketIO.Observables.MessageObservable;
import com.sneydr.roomrv2.SocketIO.Observers.JoinObserver;

import java.util.List;

public class JoinCallback extends SocketCallback implements JoinObservable {


    @Override
    public void call(Object... args) {
        String string = (String) args[0];
        List<Message> messages = jsonParser.parseMessages(string);
        notify(messages);
    }




    @Override
    public void notify(List<Message> messages) {
        JoinObserver joinObserver = (JoinObserver) this.observer;
        joinObserver.onJoin(messages);
    }
}
