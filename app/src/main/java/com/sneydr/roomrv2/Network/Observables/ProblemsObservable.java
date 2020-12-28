package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.Problem.Problem;

import java.util.List;

public interface ProblemsObservable extends NetworkObservable {

    void notifyProblems(List<Problem> problems);

}
