package com.sneydr.roomrv2.Repositories;

import android.app.Application;

import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import okhttp3.Request;

public class DocumentRepository extends Repository{



    public DocumentRepository(Application application) {
        super(application);
    }


    public void getDocuments(int houseId, String authToken, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.getDocumentsByHouseId(houseId, authToken);
            network.send(request, NetworkCallbackType.GetDocuments, observer);
        }
    }
}
