package com.sneydr.roomrv2.Entities.House;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sneydr.roomrv2.Entities.Users.Homeowner;
import com.sneydr.roomrv2.Entities.Users.Tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Document {



    private int houseId;
    private String province;
    private String description;
    private String name;
    @Nullable
    private String documentURL;


    public Document() {

    }

    public int getHouseId() {
        return houseId;
    }

    public String getProvince() {
        return province;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getDocumentURL() {
        return documentURL;
    }
}
