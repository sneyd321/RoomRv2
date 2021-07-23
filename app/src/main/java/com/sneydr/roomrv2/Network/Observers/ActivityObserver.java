package com.sneydr.roomrv2.Network.Observers;

import java.io.File;

public interface ActivityObserver extends NetworkObserver {

    void onFile(File file);

}
