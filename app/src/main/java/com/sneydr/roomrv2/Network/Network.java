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

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Network {


    private final String SERVER_URL = "http://192.168.0.115:8080/api/v1/";
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final MediaType JPG = MediaType.parse("image/jpg");
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

    public boolean isNetworkAvailable(Application application) {
        final ConnectivityManager cm = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }



    public void send(Request request, NetworkCallbackType type, NetworkObserver observer) {
        NetworkCallback callback = factory.getOkHttpCallback(type);
        callback.registerObserver(observer);
        client.newCall(request).enqueue(callback);
    }




    public Request loginHomeowner(Login login) {
    JSONParser jsonParser = JSONParser.getInstance();
    RequestBody body = RequestBody.create(JSON, jsonParser.loginToJson(login));
    return new Request.Builder()
            .url(SERVER_URL + "Login/Homeowner")
            .post(body)
            .build();
    }

    public Request loginTenant(Login login) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.loginToJson(login));
        return new Request.Builder()
                .url(SERVER_URL + "Login/Tenant")
                .post(body)
                .build();
    }

    public Request testServer() {
        return new Request.Builder()
                .url(SERVER_URL)
                .get()
                .build();
    }

    public Request postHomeowner(Homeowner homeowner) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.homeownerToJson(homeowner));
        return new Request.Builder()
                .url(SERVER_URL + "Homeowner")
                .post(body)
                .build();
    }

    public Request getHomeowner(int homeownerId) {
        Request request = new Request.Builder()
                .url(SERVER_URL + "Homeowner/" + homeownerId)
                .build();
        return request;
    }

    public Request postHouse(House house) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.houseToJson(house));
        return new Request.Builder()
                .url(SERVER_URL + "House")
                .post(body)
                .build();
    }

    public Request getHouses(Homeowner homeowner) {
        return new Request.Builder()
                .url(SERVER_URL + "Homeowner/" + homeowner.getHomeownerId() + "/House")
                .build();
    }

    public Request getHouse(int houseId) {
        return new Request.Builder()
                .url(SERVER_URL + "House/" + houseId)
                .build();
    }


    public Request postTenant(Tenant tenant) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.tenantToJson(tenant));
        return new Request.Builder()
                .url(SERVER_URL + "Tenant")
                .post(body)
                .build();
    }

    public Request getTenant(int tenantId) {
        return new Request.Builder()
                .url(SERVER_URL + "Tenant/" + tenantId)
                .build();
    }

    public Request getTenants(int houseId) {
        return new Request.Builder()
                .url(SERVER_URL + "House/" + houseId + "/Tenant")
                .build();
    }

    public Request putTenant(Tenant tenant) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.tenantToJson(tenant));
        return new Request.Builder()
                .url(SERVER_URL + "Tenant/" + tenant.getTenantId() + "/approve")
                .put(body)
                .build();
    }

    public Request postLease(Lease lease) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.leaseToJson(lease));
        return new Request.Builder()
                .url(SERVER_URL + "Lease")
                .post(body)
                .build();
    }

    public Request postProblem(Problem problem, File file) {

        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(JPG, file))
                .addFormDataPart("data", jsonParser.problemToJson(problem))
                .build();
        return new Request.Builder()
                .url(SERVER_URL + "Problem")
                .post(body)
                .build();
    }

    public Request putProblem(Problem problem) {
        JSONParser jsonParser = JSONParser.getInstance();
        RequestBody body = RequestBody.create(JSON, jsonParser.problemToJson(problem));
        return new Request.Builder()
                .url(SERVER_URL + "Homeowner/Problem/" + problem.getProblemId())
                .put(body)
                .build();
    }

    public Request getProblemsByHouseId(int houseId) {
        return new Request.Builder()
                .url(SERVER_URL + "Problem/" + houseId)
                .build();
    }

    public Request getProblem(int id) {
        return new Request.Builder()
                .url(SERVER_URL + "Problem/")
                .build();
    }


}
