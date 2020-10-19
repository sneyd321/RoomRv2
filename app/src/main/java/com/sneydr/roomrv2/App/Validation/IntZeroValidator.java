package com.sneydr.roomrv2.App.Validation;

public class IntZeroValidator extends Validator<Integer> {

    public IntZeroValidator(String errorMessage)  {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean validate(Integer integer) {
        return integer != 0;
    }
}
