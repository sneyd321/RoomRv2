package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.App.Permission;

public interface InternetPermissionObservable extends NetworkObservable {

    void notifyNoInternetPermission(Permission permission);
}
