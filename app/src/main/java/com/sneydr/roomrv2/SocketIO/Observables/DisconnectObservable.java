package com.sneydr.roomrv2.SocketIO.Observables;

import com.sneydr.roomrv2.Entities.Message.Message;

public interface DisconnectObservable extends SocketIOObservable {

    void notify(Message message);

}
