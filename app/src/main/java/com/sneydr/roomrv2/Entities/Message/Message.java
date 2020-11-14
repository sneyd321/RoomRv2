package com.sneydr.roomrv2.Entities.Message;

import androidx.room.Ignore;

import com.sneydr.roomrv2.Entities.RentDetails.CalendarHandler;


public class Message {

    private int messageId;
    private String message;
    private String timestamp;
    private int userId;
    private String userName;
    private int houseId;
    @Ignore
    private transient CalendarHandler calendarHandler;


    public Message(String message, int userId, String userName, int houseId) {
        this.message = message;
        calendarHandler = new CalendarHandler();
        this.timestamp = calendarHandler.getNow();
        this.userId = userId;
        this.userName = userName;
        this.houseId = houseId;
    }



    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }
}
