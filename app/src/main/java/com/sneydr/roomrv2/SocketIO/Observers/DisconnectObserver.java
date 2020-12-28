package com.sneydr.roomrv2.SocketIO.Observers;

import com.sneydr.roomrv2.Entities.Message.Message;

public interface DisconnectObserver extends SocketIOObserver {
    void onDisconnect(Message message);

}
