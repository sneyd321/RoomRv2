package com.sneydr.roomrv2.Entities.House.Location;


public class RentalUnitLocation extends Location{

    private String unitName;
    private boolean isCondo;
    private int parkingSpaces;

    public RentalUnitLocation(String address, String city, String province, String postalCode, String unitName, boolean isCondo, int parkingSpaces) {
        super(address, city, province, postalCode);
        this.unitName = unitName;
        this.isCondo = isCondo;
        this.parkingSpaces = parkingSpaces;
    }

    public RentalUnitLocation(int streetNumber, String streetName, String city, String province, String postalCode, String unitName, boolean isCondo, int parkingSpaces) {
        super(streetNumber, streetName, city, province, postalCode);
        this.unitName = unitName;
        this.isCondo = isCondo;
        this.parkingSpaces = parkingSpaces;
    }

    public boolean isCondo() {
        return isCondo;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public String getUnitName() {
        return unitName;
    }


    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setCondo(boolean condo) {
        isCondo = condo;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }
}
