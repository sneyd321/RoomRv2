package com.sneydr.roomrv2.SocketIO.Observables;

import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.SocketIO.Observers.SocketIOObserver;

import java.util.List;

public interface JoinObservable extends SocketIOObservable {

    void notify(List<Message> messages);

}
