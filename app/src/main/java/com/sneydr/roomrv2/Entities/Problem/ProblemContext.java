package com.sneydr.roomrv2.Entities.Problem;

public class ProblemContext {

    private ProblemStatus problemStatus;

    ProblemContext() {
        this.problemStatus = null;
    }

    public void setState(ProblemStatus status) {
        this.problemStatus = status;
    }

    public ProblemStatus getStatus() {
        return this.problemStatus;
    }





}
