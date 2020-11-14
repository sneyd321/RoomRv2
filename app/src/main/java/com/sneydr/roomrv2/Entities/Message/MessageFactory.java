package com.sneydr.roomrv2.Entities.Message;

public class MessageFactory {


    private int userId;
    private String userName;
    private int houseId;

    public MessageFactory(int userId, String userName, int houseId) {
        this.userId = userId;
        this.userName = userName;
        this.houseId = houseId;
    }


    public Message getMessage(String message) {
        return new Message(message, userId, userName, houseId);
    }



}
