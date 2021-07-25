package com.sneydr.roomrv2.Entities.House.Location;

import androidx.room.Ignore;

import java.lang.reflect.AccessibleObject;

public abstract class Location extends AccessibleObject {

    private int streetNumber;
    private String streetName;
    protected String city;
    protected String province;
    protected String postalCode;

    public String getFormattedPrimaryAddress() { return this.getStreetNumber() + " " + getStreetName(); }
    public String getFormattedSecondaryAddress() { return getCity() + ", " + getProvince() + " " + getPostalCode(); }


    public String getCity() {
        return city;
    }
    public String getProvince() {
        return province;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public int getStreetNumber() {
        return streetNumber;
    }
    public String getStreetName() {
        return streetName;
    }
}
