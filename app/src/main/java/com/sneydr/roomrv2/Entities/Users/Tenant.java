package com.sneydr.roomrv2.Entities.Users;

public class Tenant extends User {

    private int tenantId;
    private int houseId;
    private boolean isApproved;

    public Tenant(String firstName, String lastName, String email, String password, int houseId) {
        super(firstName, lastName, email, password);
        this.houseId = houseId;
        this.isApproved = false;

    }

    public Tenant(String firstName, String lastName, String email, int houseId, boolean isApproved) {
        super(firstName, lastName, email);
        this.houseId = houseId;
        this.isApproved = isApproved;
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
}
