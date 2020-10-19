package com.sneydr.roomrv2.Entities.Location;

import androidx.room.Ignore;

public abstract class Location {

    private int streetNumber;
    private String streetName;
    @Ignore
    protected String address;
    protected String city;
    protected String province;
    protected String postalCode;


    protected Location(String address, String city, String province, String postalCode) {
        this.streetNumber = getStreetNumberFromAddress(address);
        this.streetName = getStreetNameFromAddress(address);
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    protected Location(int streetNumber, String streetName, String city, String province, String postalCode) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
    }

    public String getFormattedPrimaryAddress() {
        return this.getStreetNumber() + " " + getStreetName();
    }

    public String getFormattedSecondaryAddress() {
        return getCity() + ", " + getProvince() + " " + getPostalCode();
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }


    public String getPostalCode() {
        return postalCode;
    }


    private int getStreetNumberFromAddress(String address) {
        String[] split = address.split(" ", 2);
        try {
            return Integer.parseInt(split[0]);
        }
        catch (NumberFormatException ex) {
            return 0;
        }
    }

    private String getStreetNameFromAddress(String address) {
        String[] split = address.split(" ", 2);
        try {
            return split[1];
        }
        catch (IndexOutOfBoundsException ex) {
            return "";
        }
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}
