package com.sneydr.roomrv2.Entities.Problem;

import com.sneydr.roomrv2.R;

public class CompletedStatus implements ProblemStatus {
    @Override
    public String getName() {
        return "Completed";
    }

    @Override
    public int getColour() {
        return R.color.Green;
    }
}
