package com.sneydr.roomrv2.Entities.House;





import androidx.annotation.Nullable;

import com.sneydr.roomrv2.Entities.House.Amenity.Amenity;
import com.sneydr.roomrv2.Entities.House.Location.HomeownerLocation;
import com.sneydr.roomrv2.Entities.House.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Entities.House.RentDetails.RentDetails;

import com.sneydr.roomrv2.Entities.House.Utility.Utility;


import java.util.ArrayList;
import java.util.List;

public class House {

    private int houseId;
    private String authToken;
    private RentalUnitLocation rentalUnitLocation;
    private HomeownerLocation homeownerLocation;


    public House(String authToken, HomeownerLocation homeownerLocation, RentalUnitLocation rentalUnitLocation) {
        this.authToken = authToken;
        this.rentalUnitLocation = rentalUnitLocation;
        this.homeownerLocation = homeownerLocation;
    }

    public House(int houseId, String authToken, HomeownerLocation homeownerLocation, RentalUnitLocation rentalUnitLocation) {
        this.houseId = houseId;
        this.authToken = authToken;
        this.rentalUnitLocation = rentalUnitLocation;
        this.homeownerLocation = homeownerLocation;
    }

    public HomeownerLocation getHomeownerLocation() {
        return homeownerLocation;
    }
    public RentalUnitLocation getRentalUnitLocation() {
        return rentalUnitLocation;
    }

    public String getFormattedHouseId() {
        return "House Id: " + Integer.toString(this.houseId);
    }


    public int getHouseId() {
        return houseId;
    }

    public String getAuthToken() {
        return authToken;
    }



}
