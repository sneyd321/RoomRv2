package com.sneydr.roomrv2.Entities.House.Location;


public class RentalUnitLocation extends Location{

    private String unitName;
    private boolean isCondo;
    private int parkingSpaces;


    public boolean isCondo() {
        return isCondo;
    }
    public int getParkingSpaces() {
        return parkingSpaces;
    }
    public String getUnitName() {
        return unitName;
    }

}
