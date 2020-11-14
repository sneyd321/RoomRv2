package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Network.Observables.ProblemObservable;
import com.sneydr.roomrv2.Network.Observers.ProblemObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetProblemsCallback extends NetworkCallback implements ProblemObservable {


    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            List<Problem> problems = jsonParser.parseProblem(response.body().string());
            notifyProblems(problems);
        }
        else {
            notifyFailure(response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyProblems(List<Problem> problems) {
        ProblemObserver problemObserver = (ProblemObserver) this.observer;
        problemObserver.onProblems(problems);
    }
}
