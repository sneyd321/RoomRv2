package com.sneydr.roomrv2.Network.Observables;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.util.List;

public interface ProblemObservable extends NetworkObservable {

    void notifyProblem(Problem problem);
}
