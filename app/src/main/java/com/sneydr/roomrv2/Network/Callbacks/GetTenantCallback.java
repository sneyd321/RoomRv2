package com.sneydr.roomrv2.Network.Callbacks;

import com.sneydr.roomrv2.Network.JSONParser;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class GetTenantCallback extends NetworkCallback {

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        super.onResponse(call, response);
        if (response.isSuccessful()){
            JSONParser jsonParser = JSONParser.getInstance();
            Tenant tenant = jsonParser.parseTenant(response.body().string());
            notifyTenant(tenant);
            response.close();
            return;
        }
        notifyFailure(response.body().string());
        response.close();
    }
}
