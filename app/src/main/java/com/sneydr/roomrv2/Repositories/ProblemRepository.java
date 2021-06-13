package com.sneydr.roomrv2.Repositories;



import android.app.Application;

import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Network.Network;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.io.File;

import okhttp3.Request;

public class ProblemRepository extends Repository{


    public ProblemRepository(Application application) {
        super(application);
    }


    public void update(Problem problem, String authToken, NetworkObserver observer) {
        if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
            Request request = network.putProblem(problem, authToken);
            network.send(request, NetworkCallbackType.GetProblem, observer);
        }
    }

    public void getProblemByHouseId(int houseId, String authToken, NetworkObserver observer) {
        //if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)){
        Request request = network.getProblemsByHouseId(houseId, authToken);
        network.send(request, NetworkCallbackType.GetProblems, observer);
        //}
    }

    public void getProblemById(int id, String authToken, NetworkObserver observer){
        //if (doesHaveInternet(observer) && doesHaveInternetPermission(observer)) {
        Request request = network.getProblem(id, authToken);
        network.send(request, NetworkCallbackType.GetProblem, observer);
        //}


    }




}
