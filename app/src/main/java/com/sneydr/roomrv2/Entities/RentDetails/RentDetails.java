package com.sneydr.roomrv2.Entities.RentDetails;

import java.util.ArrayList;
import java.util.List;


public class RentDetails {

    private String rentDueDate;
    private int baseRent;
    private int parkingAmount;

    private List<PaymentOption> paymentOptions;

    public RentDetails(String rentDueDate, int baseRent, int parkingAmount) {
        this.rentDueDate = rentDueDate;
        this.baseRent = baseRent;
        this.parkingAmount = parkingAmount;
        this.setPaymentOptions(new ArrayList<>());
    }

    public void addPaymentOption(PaymentOption paymentOption){
        this.getPaymentOptions().add(paymentOption);
    }


    public String getRentDueDate() {
        return rentDueDate;
    }


    public int getBaseRent() {
        return baseRent;
    }


    public int getParkingAmount() {
        return parkingAmount;
    }


    public List<PaymentOption> getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(List<PaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public String getRentMadePayableTo() {
        for (PaymentOption paymentOption : this.paymentOptions) {
            if (paymentOption instanceof ChequePaymentOption) {
                return paymentOption.rentMadePayableTo;
            }
        }
        return null;
    }

}
