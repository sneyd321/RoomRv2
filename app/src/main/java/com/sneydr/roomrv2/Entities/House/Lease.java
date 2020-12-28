package com.sneydr.roomrv2.Entities.House;

import androidx.annotation.RequiresApi;

import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lease {


    private List<Tenant> tenants;
    private String startDate;
    private String endDate;
    private int houseId;
    private String authToken;


    public Lease(List<Tenant> tenants, String startDate, String endDate, int houseId, String authToken) {
        this.tenants = filterApprovedTenants(tenants);
        this.startDate = startDate;
        this.endDate = endDate;
        this.houseId = houseId;
        this.authToken = authToken;
    }

    @RequiresApi(api = 24)
    private List<Tenant> filterTenants(List<Tenant> tenants) {
        return tenants.stream().filter(tenant -> tenant.isApproved()).collect(Collectors.toList());
    }

    private List<Tenant> filterApprovedTenants(List<Tenant> tenants) {
        List<Tenant> filteredList = new ArrayList<>();
        for (Tenant tenant : tenants) {
            if (tenant.isApproved()){
                filteredList.add(tenant);
            }
        }
        return filteredList;
    }


    public String getQueueName() {
        return authToken;
    }
}
