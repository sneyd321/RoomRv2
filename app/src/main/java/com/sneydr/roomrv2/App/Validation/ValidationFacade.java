package com.sneydr.roomrv2.App.Validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationFacade {


    private static ValidationFacade instance = null;
    private List<Validator> validatorList;


    public static ValidationFacade getInstance() {
        if (instance == null) {
            return new ValidationFacade();
        }
        return instance;
    }

    private ValidationFacade() {
        validatorList = new ArrayList<>();
    }

    public void addValidator(Validator validator) {
        this.validatorList.add(validator);
    }

    public String validate(String s) {
        for (Validator validator : validatorList) {
            if (!validator.validate(s)) {
                return validator.getErrorMessage();
            }
        }
        return "";
    }

    public String validate(int i) {
        for (Validator validator : validatorList) {
            if (!validator.validate(i)) {
                return validator.getErrorMessage();
            }
        }
        return "";
    }
}
