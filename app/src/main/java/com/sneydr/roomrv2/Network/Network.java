package com.sneydr.roomrv2.Network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackFactory;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import android.util.Base64;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class Network {


    private final String SERVER_URL = "http://192.168.0.115:8080/api/v1/";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;
    private NetworkCallbackFactory factory;

    private static Network instance;

    public static Network getInstance() {
        if (instance == null){
            instance = new Network();
        }
        return instance;
    }

    private Network() {
        client = new OkHttpClient();
        factory = new NetworkCallbackFactory();
    }

    private String toBase64(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);
    }

    public boolean isNetworkAvailable(Application application) {
        final ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void send(Request request, NetworkCallbackType type, NetworkObserver observer) {
        NetworkCallback callback = factory.getNetworkCallback(type, observer);
        client.newCall(request).enqueue(callback);
    }

    public Request loginHomeowner(Login login) {
        return new Request.Builder()
                .url(SERVER_URL + "Login")
                .addHeader("Authorization", "Basic " + toBase64(login.getEmail() + ":" + login.getPassword()))
                .build();
    }

    public Request getSignInURL() {
        return new Request.Builder()
                .url(SERVER_URL)
                .build();
    }

    public Request getHomeowner(String authToken) {
        Request request = new Request.Builder()
                .url(SERVER_URL + "Homeowner")
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
        return request;
    }

    public Request postHouse(House house) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.houseToJson(house));
        return new Request.Builder()
                .url(SERVER_URL + "House")
                .addHeader("Authorization", "Bearer " + house.getAuthToken())
                .post(body)
                .build();
    }

    public Request getHouses(String homeownerId) {
        return new Request.Builder()
                .url(SERVER_URL + "Homeowner/House")
                .addHeader("Authorization", "Bearer " + homeownerId)
                .build();
    }



    public Request getTenants(int houseId, String authToken) {
        return new Request.Builder()
                .url(SERVER_URL + "House/" + houseId + "/Tenant")
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

    public Request putTenant(Tenant tenant, String authToken) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.tenantToJson(tenant));
        return new Request.Builder()
                .url(SERVER_URL + "Tenant/" + tenant.getTenantId() + "/Approve")
                .addHeader("Authorization", "Bearer " + authToken)
                .put(body)
                .build();
    }

    public Request postLease(Lease lease, String authToken) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.leaseToJson(lease));
        return new Request.Builder()
                .url(SERVER_URL + "Lease")
                .addHeader("Authorization", "Bearer " + authToken)
                .post(body)
                .build();
    }

    public Request putProblem(Problem problem, String authToken) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.problemToJson(problem));
        return new Request.Builder()
                .url(SERVER_URL + "Problem/" + problem.getProblemId() + "/Status")
                .addHeader("Authorization", "Bearer " + authToken)
                .put(body)
                .build();
    }

    public Request getProblemsByHouseId(int houseId, String authToken) {
        return new Request.Builder()
                .url(SERVER_URL + "House/" + houseId + "/Problem")
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

    public Request getProblem(int problemId, String authToken) {
        return new Request.Builder()
                .url(SERVER_URL + "Problem/" + problemId)
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

}
