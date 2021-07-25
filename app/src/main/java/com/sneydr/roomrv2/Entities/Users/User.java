package com.sneydr.roomrv2.Entities.Users;

import androidx.annotation.Nullable;

import java.lang.reflect.AccessibleObject;

public abstract class User extends AccessibleObject {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String authToken;
    @Nullable
    private String imageURL;


    public String getFirstName() { return this.firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAuthToken() { return authToken; }
    @Nullable
    public String getImageURL() { return imageURL; }


    public String getFullName() { return this.firstName + " " + this.lastName; }

}
