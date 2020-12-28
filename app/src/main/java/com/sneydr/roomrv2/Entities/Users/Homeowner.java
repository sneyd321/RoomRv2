package com.sneydr.roomrv2.Entities.Users;


public class Homeowner extends User {


    private String phoneNumber;
    private String authToken;



    public Homeowner(String firstName, String lastName, String email, String password, String phoneNumber) {
        super(firstName, lastName, email, password);
        this.phoneNumber = phoneNumber;
    }


    public Homeowner(String firstName, String lastName, String email, String phoneNumber, String authToken, boolean dummy) {
        super(firstName, lastName, email);
        this.phoneNumber = phoneNumber;
        this.authToken = authToken;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAuthToken() {
        return authToken;
    }
}
