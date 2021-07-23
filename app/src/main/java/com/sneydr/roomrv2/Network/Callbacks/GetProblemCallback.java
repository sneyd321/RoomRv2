package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observables.ProblemObservable;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetProblemCallback extends NetworkCallback implements ProblemObservable {
    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            Problem problem = jsonParser.parseProblem(response.body().string());
            notifyProblem(problem);
        }
        else {
            notifyFailure("Problem", response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyProblem(Problem problem) {
        ProblemObserver problemObserver = (ProblemObserver) observer;
        problemObserver.onProblem(problem);
    }
}
