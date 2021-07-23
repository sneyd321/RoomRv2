package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Network.Observables.DocumentsObservable;
import com.sneydr.roomrv2.Network.Observers.DocumentsObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetDocumentsCallback extends NetworkCallback implements DocumentsObservable {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            List<Document> documents = jsonParser.parseDocuments(response.body().string());
            notifyDocuments(documents);
        }
        else {
            notifyFailure("Documents", response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyDocuments(List<Document> documents) {
        DocumentsObserver documentObserver = (DocumentsObserver) this.observer;
        documentObserver.onDocuments(documents);
    }
}
