package com.sneydr.roomrv2.App.Validation;

public  class IntTooLowValidator extends Validator<Integer> {

    public IntTooLowValidator(String errorMessage, int threshold) {
        this.errorMessage = errorMessage;
        this.threshold = threshold;
    }

    @Override
    public boolean validate(Integer integer) {
        return integer >= threshold;
    }
}
