package com.sneydr.roomrv2.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sneydr.roomrv2.Entities.House.House;
import com.sneydr.roomrv2.Entities.House.Lease;
import com.sneydr.roomrv2.Entities.Login.Login;
import com.sneydr.roomrv2.Entities.Message.Message;
import com.sneydr.roomrv2.Entities.Problem.Problem;
import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONParser {


    private static JSONParser instance = null;
    private Gson gson;

    public static JSONParser getInstance() {
        if (instance == null){
            instance = new JSONParser();
        }
        return instance;
    }

    public JSONParser() {
        gson = new Gson();
    }

    public String leaseToJson(Lease lease) {
        return gson.toJson(lease, Lease.class);
    }


    public String parseToken(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("token");
    }

    public String homeownerToJson(Homeowner homeowner) {
        return gson.toJson(homeowner, Homeowner.class);
    }

    public String loginToJson(Login login) {
        return gson.toJson(login, Login.class);
    }

    public Homeowner parseHomeowner(String response) {
        return gson.fromJson(response, Homeowner.class);
    }

    public List<House> parseHouses(String response) {
        Type houseType = new TypeToken<ArrayList<House>>(){}.getType();
        return gson.fromJson(response, houseType);
    }

    public String houseToJson(House house) {
        return gson.toJson(house, House.class);
    }

    public String tenantToJson(Tenant tenant) {
        return gson.toJson(tenant, Tenant.class);
    }

    public Tenant parseTenant(String response) {
        return gson.fromJson(response, Tenant.class);
    }

    public List<Tenant> parseTenants(String response) {
        Type tenantType = new TypeToken<ArrayList<Tenant>>(){}.getType();
        return gson.fromJson(response, tenantType);
    }

    public House parseHouse(String response) {
        return gson.fromJson(response, House.class);
    }

    public String problemToJson(Problem problem) {
        return gson.toJson(problem, Problem.class);
    }

    public List<Problem> parseProblem(String response) {
        Type problemType = new TypeToken<ArrayList<Problem>>(){}.getType();
        return gson.fromJson(response, problemType);
    }

    public Message parseMessage(String response) {
        return gson.fromJson(response, Message.class);
    }


    public String messageToJson(Message message) {
        return gson.toJson(message, Message.class);
    }


}
