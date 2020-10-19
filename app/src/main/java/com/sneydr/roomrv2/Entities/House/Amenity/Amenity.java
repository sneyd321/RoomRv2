package com.sneydr.roomrv2.Entities.House.Amenity;


public class Amenity {

    protected String name;
    protected boolean includedInRent;
    protected boolean payPerUse;



    public Amenity(String name, boolean includedInRent) {
        this.name = name;
        this.includedInRent = includedInRent;
        this.payPerUse = false;

    }

    public String getName() {
        return name;
    }

    public boolean isIncludedInRent() {
        return includedInRent;
    }

    public void setIncludedInRent(boolean includedInRent) {
        this.includedInRent = includedInRent;
    }


    public boolean isPayPerUse() {
        return payPerUse;
    }

    public void setPayPerUse(boolean payPerUse) {
        this.payPerUse = payPerUse;
    }
}
