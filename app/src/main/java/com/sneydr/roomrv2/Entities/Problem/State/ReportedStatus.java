package com.sneydr.roomrv2.Entities.Problem.State;

import com.sneydr.roomrv2.Entities.Problem.State.ProblemStatus;
import com.sneydr.roomrv2.R;

public class ReportedStatus implements ProblemStatus {
    @Override
    public String getName() {
        return "Reported";
    }

    @Override
    public int getColour() {
        return R.color.Purple;
    }
}
