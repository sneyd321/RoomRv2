package com.sneydr.roomrv2.Entities.House;



import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.sneydr.roomrv2.Entities.House.Amenity.Amenity;
import com.sneydr.roomrv2.Entities.Location.RentalUnitLocation;
import com.sneydr.roomrv2.Entities.RentDetails.RentDetails;

import com.sneydr.roomrv2.Entities.House.Utility.Utility;


import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "house_table")
public class House {

    @PrimaryKey
    private int houseId;
    private int homeownerId;
    @Embedded
    private RentalUnitLocation rentalUnitLocation;
    @Embedded
    private RentDetails rentDetails;

    private List<Amenity> amenities;

    private List<Utility> utilities;


    @Ignore
    public House(int homeownerId, RentalUnitLocation rentalUnitLocation, RentDetails rentDetails) {
        this.homeownerId = homeownerId;
        this.rentalUnitLocation = rentalUnitLocation;
        this.rentDetails = rentDetails;
        this.amenities = new ArrayList<>();
        this.utilities = new ArrayList<>();
    }

    public House(int houseId, int homeownerId, RentalUnitLocation rentalUnitLocation, RentDetails rentDetails) {
        this.houseId = houseId;
        this.homeownerId = homeownerId;
        this.rentalUnitLocation = rentalUnitLocation;
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

    public int getHomeownerId() {
        return homeownerId;
    }

    public void setHomeownerId(int homeownerId) {
        this.homeownerId = homeownerId;
    }
}
