package com.sneydr.roomrv2.Entities.House;


import com.sneydr.roomrv2.Entities.House.Location.RentalUnitLocation;


import java.lang.reflect.AccessibleObject;

public class House extends AccessibleObject {

    private int houseId;
    private String authToken;
    private RentalUnitLocation rentalUnitLocation;

    public RentalUnitLocation getRentalUnitLocation() {
        return rentalUnitLocation;
    }

    public String getFormattedHouseId() {
        return "House Key: " + Integer.toString(this.houseId);
    }

    public int getHouseId() {
        return houseId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setRentalUnitLocation(RentalUnitLocation rentalUnitLocation) { this.rentalUnitLocation = rentalUnitLocation; }
}
