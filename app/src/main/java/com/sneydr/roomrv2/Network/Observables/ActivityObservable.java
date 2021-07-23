package com.sneydr.roomrv2.Network.Observables;

import java.io.File;

public interface ActivityObservable extends NetworkObservable {

    void notifyObserver(File file);

}
