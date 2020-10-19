package com.sneydr.roomrv2.App.UI;

public class LeaseText {
    private String text;
    private int x;
    private int y;


    public LeaseText(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
