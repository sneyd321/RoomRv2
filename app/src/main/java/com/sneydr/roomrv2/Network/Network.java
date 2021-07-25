package com.sneydr.roomrv2.Network;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sneydr.roomrv2.App.App;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Document;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallback;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackFactory;
import com.sneydr.roomrv2.Network.Callbacks.NetworkCallbackType;
import com.sneydr.roomrv2.Entities.Users.Tenant;
import com.sneydr.roomrv2.Network.Observers.NetworkObserver;

import java.io.File;

import android.util.Base64;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.sneydr.roomrv2.App.Constants.SERVER_URL;

public class Network {


    //private static final String SERVER_URL = "http://192.168.100.109:8080/homeowner-gateway/v1/";
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
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(App.getCache(),"OkHttpCache"), cacheSize);
        client = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new ForceCacheInterceptor()).build();
        factory = new NetworkCallbackFactory();
    }

    private String toBase64(String input) {
        return Base64.encodeToString(input.getBytes(), Base64.NO_WRAP);
    }

    public String getServerUrl() {
        return SERVER_URL;
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

    public Request getURL(String url, String authToken) {
        return new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + authToken)
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
                //.cacheControl(new CacheControl.Builder().maxStale(600, TimeUnit.SECONDS).build())
                .addHeader("Authorization", "Bearer " + homeownerId)
                .build();
    }



    public Request getTenants(int houseId, String authToken) {
        return new Request.Builder()
                .url(SERVER_URL + "House/" + houseId + "/Tenant")
                //.cacheControl(new CacheControl.Builder().maxStale(600, TimeUnit.SECONDS).build())
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

    public Request postLease(Document lease, String authToken) {
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
                //.cacheControl(new CacheControl.Builder().maxStale(600, TimeUnit.SECONDS).build())
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

    public Request getProblem(int problemId, String authToken) {
        return new Request.Builder()
                .url(SERVER_URL + "Problem/" + problemId)
                .cacheControl(new CacheControl.Builder().maxStale(600, TimeUnit.SECONDS).build())
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

    public Request getDocumentsByHouseId(int houseId, String authToken) {
        return new Request.Builder()
                .url(SERVER_URL + "Document/" + houseId)
                //.cacheControl(new CacheControl.Builder().maxStale(600, TimeUnit.SECONDS).build())
                .addHeader("Authorization", "Bearer " + authToken)
                .build();
    }

    public Request uploadProfilePicture(String authToken, File file){
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(JPG, file))
                .build();
        return new Request.Builder()
                .url(SERVER_URL + "Homeowner/Profile")
                .post(body)
                .addHeader("Authorization", "Bearer " + authToken)
                .addHeader("Accept-Encoding", "identity")
                .addHeader("Connection", "close")
                .build();
    }

    public Request uploadHousePicture(String authToken, int houseId, File file){
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(), RequestBody.create(JPG, file))
                .build();
        return new Request.Builder()
                .url(SERVER_URL + "House/" + houseId + "/Profile")
                .post(body)
                .addHeader("Authorization", "Bearer " + authToken)
                .addHeader("Accept-Encoding", "identity")
                .addHeader("Connection", "close")
                .build();
    }

    public Request pollTaskId(String taskId) {
        return new Request.Builder()
                .url(SERVER_URL + "Image/Task" + taskId)
                //.cacheControl(new CacheControl.Builder().maxStale(600, TimeUnit.SECONDS).build())
                .build();
    }

}
