package com.sneydr.roomrv2.Entities.Problem.State;

import com.sneydr.roomrv2.Entities.Problem.State.ProblemStatus;
import com.sneydr.roomrv2.R;

public class ViewedStatus implements ProblemStatus {
    @Override
    public String getName() {
        return "Viewed";
    }

    @Override
    public int getColour() {
        return R.color.Yellow;
    }
}
