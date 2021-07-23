package com.sneydr.roomrv2.Entities.Users;

import androidx.annotation.Nullable;

public class Tenant extends User {

    private int tenantId;
    private int houseId;
    private boolean isApproved;
    private String phoneNumber;
    @Nullable
    private String imageURL;

    public Tenant(String firstName, String lastName, String email, String password, int houseId, String phoneNumber) {
        super(firstName, lastName, email, password);
        this.houseId = houseId;
        this.isApproved = false;
        this.phoneNumber = phoneNumber;
        this.imageURL = null;

    }

    public Tenant(String firstName, String lastName, String email, int houseId, boolean isApproved, String phoneNumber, @Nullable String imageURL) {
        super(firstName, lastName, email);
        this.houseId = houseId;
        this.isApproved = isApproved;
        this.phoneNumber = phoneNumber;
        this.imageURL = imageURL;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public int getHouseId() {
        return houseId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    @Nullable
    public String getImageURL() {
        return imageURL;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
