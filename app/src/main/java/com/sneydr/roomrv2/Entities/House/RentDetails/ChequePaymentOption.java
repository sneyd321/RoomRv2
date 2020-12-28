package com.sneydr.roomrv2.Entities.House.RentDetails;


public class ChequePaymentOption extends PaymentOption {

    public ChequePaymentOption(String rentMadePayableTo) {
        super();
        this.name = "Cheque";
        this.rentMadePayableTo = rentMadePayableTo;
    }

    @Override
    public String getRentMadePayableTo()  {
        return this.rentMadePayableTo;
    }
}
