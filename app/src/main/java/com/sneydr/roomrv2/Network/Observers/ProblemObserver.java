package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observables.NetworkObservable;

import java.util.List;

public interface ProblemObserver extends NetworkObserver {

    void onProblem(Problem problem);

}
