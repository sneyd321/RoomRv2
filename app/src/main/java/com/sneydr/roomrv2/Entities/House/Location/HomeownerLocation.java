package com.sneydr.roomrv2.Entities.House.Location;



public class HomeownerLocation extends Location {

    private String poBox;
    private String unitNumber;

    public HomeownerLocation(String address, String city, String province, String postalCode, String unitNumber, String poBox) {
        super(address, city, province, postalCode);
        this.poBox = poBox;
        this.unitNumber = unitNumber;
    }

    public HomeownerLocation(int streetNumber, String streetName, String city, String province, String postalCode, String unitNumber, String poBox) {
        super(streetNumber, streetName, city, province, postalCode);
        this.poBox = poBox;
        this.unitNumber = unitNumber;
    }

    public String getPoBox() {
        return poBox;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

}
