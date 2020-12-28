package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.App.Permission;

public interface InternetPermissionObserver extends NetworkObserver {

    void onNoInternetPermission(Permission permission);
}
