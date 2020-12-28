package com.sneydr.roomrv2.Entities.Message;

public class MessageFactory {


    private String email;
    private String userName;
    private String userType;
    private int houseId;

    public MessageFactory(String email, String userName, String userType, int houseId) {
        this.email = email;
        this.userName = userName;
        this.userType = userType;
        this.houseId = houseId;
    }


    public Message getMessage(String message) {
        return new Message(message, email, userName, userType, houseId);
    }



}
