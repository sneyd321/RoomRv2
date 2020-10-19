package com.sneydr.roomrv2.Entities.House;

import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.List;

public class Lease {

    private House house;
    private Homeowner homeowner;
    private List<Tenant> tenants;
    private String startDate;
    private String endDate;


    public Lease(House house, Homeowner homeowner, List<Tenant> tenants) {
        this.house = house;
        this.homeowner = homeowner;
        this.tenants = tenants;
    }

    public String getQueueName() {
        return this.homeowner.getEmail();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
