package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observables.ProblemsObservable;
import com.sneydr.roomrv2.Network.Observers.ProblemsObserver;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetProblemsCallback extends NetworkCallback implements ProblemsObservable {


    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        if (response.isSuccessful()){
            List<Problem> problems = jsonParser.parseProblems(response.body().string());
            notifyProblems(problems);
        }
        else {
            notifyFailure("Problems", response.body().string());
        }
        response.close();
    }

    @Override
    public void notifyProblems(List<Problem> problems) {
        ProblemsObserver problemsObserver = (ProblemsObserver) this.observer;
        problemsObserver.onProblems(problems);
    }
}
