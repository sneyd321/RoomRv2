package com.sneydr.roomrv2.Repositories;



import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.io.File;

import okhttp3.Request;

public class ProblemRepository {

    private Network network;
    private NetworkObserver observer;


    public ProblemRepository(NetworkObserver observer) {
       this.observer = observer;
       this.network = Network.getInstance();
    }

    public void insert(Problem problem, File file) {
        Request request = network.postProblem(problem, file);
        network.send(request, NetworkCallbackType.GetProblems, observer);
    }
    public void update(Problem problem) {
        Request request = network.putProblem(problem);
        network.send(request, NetworkCallbackType.GetProblems, observer);
    }

    public void getProblemByHouseId(int houseId) {
        Request request = network.getProblemsByHouseId(houseId);
        network.send(request, NetworkCallbackType.GetProblems, observer);
    }

    public void getProblemById(int id){
        Request request = network.getProblem(id);
    }




}
