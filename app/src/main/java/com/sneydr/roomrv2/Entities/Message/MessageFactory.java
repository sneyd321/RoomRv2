package com.sneydr.roomrv2.Entities.Message;

import androidx.annotation.Nullable;

public class MessageFactory {


    private String email;
    private String userName;
    private String userType;
    private int houseId;
    @Nullable
    private String imageURL;

    public MessageFactory(String email, String userName, String userType, @Nullable String imageURL, int houseId) {
        this.email = email;
        this.userName = userName;
        this.userType = userType;
        this.houseId = houseId;
        this.imageURL = imageURL;
    }


    public Message getMessage(String message) {
        return new Message(message, email, userName, userType, imageURL, houseId);
    }

    public String getEmail() {
        return email;
    }



}
