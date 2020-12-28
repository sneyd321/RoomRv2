package com.sneydr.roomrv2.SocketIO.Observers;

import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.SocketIO.Observers.SocketIOObserver;

import java.util.List;

public interface JoinObserver extends SocketIOObserver {

    void onJoin(List<Message> messages);


}
