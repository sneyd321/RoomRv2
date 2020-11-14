package com.sneydr.roomrv2.Entities.Users;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.sneydr.roomrv2.Entities.Location.HomeownerLocation;


@Entity(tableName = "homeowner_table")
public class Homeowner extends User {

    @PrimaryKey
    private int homeownerId;
    private String phoneNumber;
    @Embedded
    private HomeownerLocation homeownerLocation;

    @Ignore
    public Homeowner(String firstName, String lastName, String email, String password, String phoneNumber, HomeownerLocation homeownerLocation) {
        super(firstName, lastName, email, password);
        this.phoneNumber = phoneNumber;
        this.homeownerLocation = homeownerLocation;
    }


    public Homeowner(int homeownerId, String firstName, String lastName, String email, String phoneNumber, HomeownerLocation homeownerLocation) {
        super(homeownerId, firstName, lastName, email);
        this.homeownerId = homeownerId;
        this.phoneNumber = phoneNumber;
        this.homeownerLocation = homeownerLocation;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public HomeownerLocation getHomeownerLocation() {
        return homeownerLocation;
    }

    public void setHomeownerLocation(HomeownerLocation homeownerLocation) {
        this.homeownerLocation = homeownerLocation;
    }

    public int getHomeownerId() {
        return homeownerId;
    }

    public void setHomeownerId(int homeownerId) {
        this.homeownerId = homeownerId;
    }
}
