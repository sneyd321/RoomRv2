package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class GetTenantsCallback extends NetworkCallback {


    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.onResponse(call, response);
        if (response.isSuccessful()){
            JSONParser jsonParser = JSONParser.getInstance();
            List<Tenant> tenants = jsonParser.parseTenants(response.body().string());
            notifyTenants(tenants);
            response.close();
            return;
        }
        notifyFailure(response.body().string());
        response.close();
    }
}
