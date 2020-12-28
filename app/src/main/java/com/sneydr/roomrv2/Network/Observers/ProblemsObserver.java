package com.sneydr.roomrv2.Network.Observers;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.util.List;

public interface ProblemsObserver extends NetworkObserver {

    void onProblems(List<Problem> problems);

}
