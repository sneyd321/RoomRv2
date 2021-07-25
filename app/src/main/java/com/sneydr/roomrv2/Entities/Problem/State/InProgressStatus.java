package com.sneydr.roomrv2.Entities.Problem.State;

import com.sneydr.roomrv2.R;

public class InProgressStatus implements ProblemStatus {
    @Override
    public String getName() {
        return "In Progress";
    }

    @Override
    public int getColour() {
        return R.color.colorSecondary;
    }


}
