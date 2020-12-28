package com.sneydr.roomrv2.SocketIO.Callbacks;

import javax.security.auth.callback.Callback;

public class CallbackFactory {


    public SocketCallback getSocketCallback(CallbackType type) {
        switch (type) {
            case onJoin:
                return new JoinCallback();
            case onMessage:
                return new MessageCallback();
            case onDisconnect:
                return new DisconnectCallback();
            default:
                return null;

        }
    }

}
