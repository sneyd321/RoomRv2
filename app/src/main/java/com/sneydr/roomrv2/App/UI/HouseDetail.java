package com.sneydr.roomrv2.App.UI;

public class HouseDetail {

    private String name;

    private int resourceId;

    public HouseDetail(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }


    public String getName() {
        return this.name;
    }


    public int getResourceId() {
        return this.resourceId;
    }

}
