package com.sneydr.roomrv2.Entities.Users;

import android.util.JsonReader;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.reflect.Field;

public class Tenant extends User {

    private int tenantId;
    private int houseId;
    private boolean isApproved;


    public boolean isApproved() {
        return isApproved;
    }
    public int getHouseId() {
        return houseId;
    }
    public int getTenantId() { return tenantId; }

    public void setHouseId(int houseId) { this.houseId = houseId; }
    public void setApproved(boolean approved) { isApproved = approved; }

}
