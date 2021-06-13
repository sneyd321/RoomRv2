package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Network.Observables.NetworkObservable;

import java.util.List;

public interface DocumentsObserver extends NetworkObserver {

    void onDocuments(List<Document> documents);


}
