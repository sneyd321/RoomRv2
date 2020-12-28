package com.sneydr.roomrv2.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Repositories.ProblemRepository;


import java.io.File;

public class ProblemViewModel extends AndroidViewModel {
    private ProblemRepository repository;

    public ProblemViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ProblemRepository(application);
    }

    public void getProblemById(int problemId, String authToken, NetworkObserver observer) {
        repository.getProblemById(problemId, authToken, observer);
    }


    private void saveProblem(Problem problem, File file, NetworkObserver observer) {
        //repository.insert(problem, file, observer);
    }

    public void updateProblem(Problem problem, String authToken, NetworkObserver observer) {
        repository.update(problem, authToken, observer);
    }

    public void loadProblems(int houseId, String authToken, NetworkObserver observer) {
        repository.getProblemByHouseId(houseId, authToken, observer);
    }


}
