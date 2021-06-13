package com.sneydr.roomrv2.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Network.Observers.DocumentsObserver;
import com.sneydr.roomrv2.Network.Observers.HomeownerObserver;
import com.sneydr.roomrv2.Repositories.DocumentRepository;
import com.sneydr.roomrv2.Repositories.HomeownerRepository;

public class DocumentViewModel extends AndroidViewModel {
    DocumentRepository repository;

    public DocumentViewModel(Application application) {
        super(application);
        repository = new DocumentRepository(application);
    }

    public void getDocuments(int houseId, String authToken, DocumentsObserver observer) {
        repository.getDocuments(houseId, authToken, observer);
    }



}
