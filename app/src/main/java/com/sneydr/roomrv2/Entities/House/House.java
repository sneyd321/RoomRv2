package com.sneydr.roomrv2.Entities.House;





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
    private RentDetails rentDetails;
    private List<Amenity> amenities;

    private List<Utility> utilities;

    public House(String authToken, HomeownerLocation homeownerLocation, RentalUnitLocation rentalUnitLocation, RentDetails rentDetails) {
        this.authToken = authToken;
        this.rentalUnitLocation = rentalUnitLocation;
        this.homeownerLocation = homeownerLocation;
        this.rentDetails = rentDetails;
        this.amenities = new ArrayList<>();
        this.utilities = new ArrayList<>();
    }

    public House(int houseId, String authToken, HomeownerLocation homeownerLocation, RentalUnitLocation rentalUnitLocation, RentDetails rentDetails) {
        this.houseId = houseId;
        this.authToken = authToken;
        this.rentalUnitLocation = rentalUnitLocation;
        this.homeownerLocation = homeownerLocation;
        this.rentDetails = rentDetails;
        this.amenities = new ArrayList<>();
        this.utilities = new ArrayList<>();
    }


    public RentalUnitLocation getRentalUnitLocation() {
        return rentalUnitLocation;
    }

    public RentDetails getRentDetails() {
        return rentDetails;
    }


    public List<Amenity> getAmenities() {
        return amenities;
    }


    public List<Utility> getUtilities() {
        return utilities;
    }

    public List<Utility> getTenantResponsibilityUtilities() {
        List<Utility> tenantUtilities = new ArrayList<>();
        for (Utility utility : this.utilities) {
            if (utility.getResponsibilityOf().equals("Tenant")) {
                tenantUtilities.add(utility);
            }
        }
        return tenantUtilities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public void setUtilities(List<Utility> utilities) {
        this.utilities = utilities;
    }

    public int getHouseId() {
        return houseId;
    }

    public String getAuthToken() {
        return authToken;
    }


}
