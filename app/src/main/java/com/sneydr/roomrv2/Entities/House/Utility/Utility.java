package com.sneydr.roomrv2.Entities.House.Utility;

public class Utility {

    protected String name;
    protected String responsibilityOf;



    protected int amount;


    protected Utility() {
        this.responsibilityOf = "Homeowner";
    }

    public String getName() {
        return name;
    }

    public String getResponsibilityOf() {
        return responsibilityOf;
    }

    public void setResponsibilityOf(String responsibilityOf) {
        this.responsibilityOf = responsibilityOf;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}


