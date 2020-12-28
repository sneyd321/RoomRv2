package com.sneydr.roomrv2.SocketIO.Observers;

import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.SocketIO.Observers.SocketIOObserver;

public interface MessageObserver extends SocketIOObserver {

    void onMessage(Message message);

}
