package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.House.Document;

import java.util.List;

public interface DocumentsObservable extends NetworkObservable {

    void notifyDocuments(List<Document> documents);

}
